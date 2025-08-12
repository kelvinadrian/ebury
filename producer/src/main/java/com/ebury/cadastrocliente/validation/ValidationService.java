package com.ebury.cadastrocliente.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço principal de validação que usa reflexão para validar automaticamente
 * todos os campos de um objeto, aplicando validadores personalizados.
 */
@Service
@Slf4j
public class ValidationService {
    
    private final List<FieldValidator> validators;
    private final Map<Class<?>, List<Field>> fieldCache = new ConcurrentHashMap<>();
    
    @Autowired
    public ValidationService(List<FieldValidator> validators) {
        this.validators = validators;
        log.info("ValidationService inicializado com {} validadores", validators.size());
        
        // Log dos validadores carregados
        for (FieldValidator validator : validators) {
            log.debug("Validador carregado: {} (prioridade: {})", 
                     validator.getClass().getSimpleName(), validator.getPriority());
        }
    }
    
    /**
     * Valida um objeto completo, incluindo campos aninhados.
     * 
     * @param object Objeto a ser validado
     * @return Lista de resultados de validação
     */
    public List<ValidationResult> validateObject(Object object) {
        List<ValidationResult> results = new ArrayList<>();
        
        if (object == null) {
            results.add(ValidationResult.invalid("Objeto não pode ser nulo"));
            return results;
        }
        
        validateObjectRecursive(object, "", results);
        return results;
    }
    
    /**
     * Valida um objeto recursivamente, incluindo campos aninhados.
     * 
     * @param object Objeto a ser validado
     * @param currentPath Caminho atual do campo
     * @param results Lista de resultados
     */
    private void validateObjectRecursive(Object object, String currentPath, List<ValidationResult> results) {
        if (object == null) {
            return;
        }
        
        Class<?> clazz = object.getClass();
        List<Field> fields = getFields(clazz);
        
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldPath = currentPath.isEmpty() ? field.getName() : currentPath + "." + field.getName();
            
            try {
                Object value = field.get(object);
                validateField(field, value, fieldPath, results);
                
                // Se o valor não é nulo e não é um tipo primitivo, valida recursivamente
                if (value != null && !isPrimitiveOrWrapper(value.getClass())) {
                    if (isCollection(value.getClass())) {
                        // Se é uma coleção, valida cada item com índice
                        validateCollection((Collection<?>) value, fieldPath, results);
                    } else {
                        // Se não é coleção, valida recursivamente
                        validateObjectRecursive(value, fieldPath, results);
                    }
                }
                
            } catch (IllegalAccessException e) {
                log.error("Erro ao acessar campo {}: {}", fieldPath, e.getMessage());
                results.add(ValidationResult.invalid("Erro interno ao validar campo: " + fieldPath, fieldPath));
            }
        }
    }
    
    /**
     * Valida uma coleção (List) recursivamente, incluindo índices para identificação de posição.
     * 
     * @param collection Coleção a ser validada
     * @param currentPath Caminho atual do campo
     * @param results Lista de resultados
     */
    private void validateCollection(Collection<?> collection, String currentPath, List<ValidationResult> results) {
        if (collection == null || collection.isEmpty()) {
            return;
        }
        
        int index = 0;
        for (Object item : collection) {
            String itemPath = currentPath + "[" + index + "]";
            
            if (item != null && !isPrimitiveOrWrapper(item.getClass())) {
                validateObjectRecursive(item, itemPath, results);
            }
            
            index++;
        }
    }
    
    /**
     * Valida um campo específico aplicando todos os validadores que verificam anotações.
     * Os validadores são aplicados em ordem de prioridade (menor número = maior prioridade).
     * 
     * @param field Campo a ser validado
     * @param value Valor do campo
     * @param fieldPath Caminho do campo
     * @param results Lista de resultados
     */
    private void validateField(Field field, Object value, String fieldPath, List<ValidationResult> results) {
        // Lista para armazenar resultados temporários
        List<ValidationResult> tempResults = new ArrayList<>();
        
        // Aplica todos os validadores que verificam anotações
        for (FieldValidator validator : validators) {
            try {
                ValidationResult result = validator.validate(field, value, fieldPath);
                if (!result.isValid()) {
                    // Adiciona a ordem do validador ao resultado para ordenação posterior
                    int ordem = getValidatorOrder(field, validator);
                    ValidationResult resultWithOrder = new ValidationResult(
                        result.isValid(), 
                        result.getMessage(), 
                        result.getFieldPath(),
                        ordem
                    );
                    tempResults.add(resultWithOrder);
                    log.debug("Validação falhou para {}: {} (validador: {}, ordem: {})", 
                             fieldPath, result.getMessage(), validator.getClass().getSimpleName(), ordem);
                }
            } catch (Exception e) {
                log.error("Erro ao executar validador {} para campo {}: {}", 
                         validator.getClass().getSimpleName(), fieldPath, e.getMessage());
                tempResults.add(ValidationResult.invalid("Erro interno no validador: " + e.getMessage(), fieldPath, 999));
            }
        }
        
        // Ordena os resultados por ordem de prioridade e adiciona à lista principal
        tempResults.stream()
                  .sorted((r1, r2) -> Integer.compare(r1.getOrdem(), r2.getOrdem()))
                  .forEach(results::add);
    }
    
    /**
     * Obtém a ordem do validador baseada na anotação do campo.
     * 
     * @param field Campo sendo validado
     * @param validator Validador sendo aplicado
     * @return Ordem do validador (menor número = maior prioridade)
     */
    private int getValidatorOrder(Field field, FieldValidator validator) {
        // Verifica todas as anotações organizadas por pacotes
        java.lang.annotation.Annotation[] annotations = field.getAnnotations();
        
        for (java.lang.annotation.Annotation annotation : annotations) {
            try {
                // Usa reflexão para obter o método 'ordem()' se existir
                java.lang.reflect.Method ordemMethod = annotation.annotationType().getMethod("ordem");
                if (ordemMethod != null) {
                    return (int) ordemMethod.invoke(annotation);
                }
            } catch (Exception e) {
                // Ignora exceções e continua verificando outras anotações
                continue;
            }
        }
        
        // Ordem padrão para validadores sem anotação específica
        return 100;
    }
    
    /**
     * Obtém todos os campos de uma classe, incluindo campos herdados.
     * 
     * @param clazz Classe
     * @return Lista de campos
     */
    private List<Field> getFields(Class<?> clazz) {
        return fieldCache.computeIfAbsent(clazz, k -> {
            List<Field> fields = new ArrayList<>();
            Class<?> currentClass = k;
            
            while (currentClass != null && currentClass != Object.class) {
                Field[] declaredFields = currentClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    // Ignora campos estáticos e sintéticos
                    if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
                        fields.add(field);
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
            
            return fields;
        });
    }
    
    /**
     * Verifica se uma classe é um tipo primitivo ou wrapper.
     * 
     * @param clazz Classe a ser verificada
     * @return true se for primitivo ou wrapper
     */
    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || 
               clazz == String.class ||
               clazz == Integer.class ||
               clazz == Long.class ||
               clazz == Double.class ||
               clazz == Float.class ||
               clazz == Boolean.class ||
               clazz == Character.class ||
               clazz == Byte.class ||
               clazz == Short.class;
    }
    
    /**
     * Verifica se uma classe é uma coleção.
     * 
     * @param clazz Classe a ser verificada
     * @return true se for uma coleção
     */
    private boolean isCollection(Class<?> clazz) {
        return java.util.Collection.class.isAssignableFrom(clazz);
    }
} 