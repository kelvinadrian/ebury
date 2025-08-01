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
        // Verifica cada tipo de anotação e retorna a ordem correspondente
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDeManutencao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDeManutencao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDePessoa.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDePessoa.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCpfCnpj.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCpfCnpj.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCodigoCorporativo.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCodigoCorporativo.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNome.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNome.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoData.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoData.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDataDesativacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDataDesativacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDesabilitado.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDesabilitado.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoAssinaturaDigital.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoAssinaturaDigital.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNegociacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNegociacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoComplementoNatureza.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoComplementoNatureza.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNaturezaJuridica.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNaturezaJuridica.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOriginador.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOriginador.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoResidencia.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoResidencia.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoGerenteAnalista.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoGerenteAnalista.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPep.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPep.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEndereco.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEndereco.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEnderecoNoExterior.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEnderecoNoExterior.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContaCorrente.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContaCorrente.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContaCorrenteTedDoc.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContaCorrenteTedDoc.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmailsDocumentos.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmailsDocumentos.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOperacoesPermitidas.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOperacoesPermitidas.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCorretorasQueRepresentam.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCorretorasQueRepresentam.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoListaDocumentos.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoListaDocumentos.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRepresentanteLegal.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRepresentanteLegal.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoIban.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoIban.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoSexo.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoSexo.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEstadoCivil.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEstadoCivil.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDocumentoIdentificacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoDocumentoIdentificacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmissorDocumento.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmissorDocumento.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoUfEmissor.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoUfEmissor.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomeMae.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomeMae.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomePai.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomePai.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNacionalidade.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNacionalidade.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoMunicipioNaturalidade.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoMunicipioNaturalidade.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoUfNaturalidade.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoUfNaturalidade.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomeConjuge.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNomeConjuge.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTelefone.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTelefone.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRenda.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRenda.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoInscricaoEstadual.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoInscricaoEstadual.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPorte.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPorte.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRamoAtividade.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRamoAtividade.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoFaturamentoMedio.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoFaturamentoMedio.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmail.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoEmail.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCep.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoCep.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoMunicipio.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoMunicipio.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoBairro.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoBairro.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoLogradouro.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoLogradouro.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNumero.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNumero.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoComplemento.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoComplemento.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoAgencia.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoAgencia.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoConta.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoConta.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoVigencia.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoVigencia.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPercentual.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPercentual.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoQuantidade.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoQuantidade.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPais.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoPais.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoIdentificacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoIdentificacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNumeroIdentificacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNumeroIdentificacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOrgaoEmissor.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOrgaoEmissor.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDocumento.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoDocumento.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoVencimento.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoVencimento.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoObservacoes.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoObservacoes.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoArquivos.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoArquivos.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoOperacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoOperacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNivel.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoNivel.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOrdenLista.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoOrdenLista.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRazaoSocial.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoRazaoSocial.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoParticipacao.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoParticipacao.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContato.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoContato.class).ordem();
        }
        if (field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoSocioAcionista.class) != null) {
            return field.getAnnotation(com.ebury.cadastrocliente.validation.annotations.ValidacaoSocioAcionista.class).ordem();
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