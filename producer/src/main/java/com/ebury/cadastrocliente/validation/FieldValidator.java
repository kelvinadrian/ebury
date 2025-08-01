package com.ebury.cadastrocliente.validation;

import java.lang.reflect.Field;

/**
 * Interface para validadores de campo baseados em anotações.
 */
public interface FieldValidator {
    
    /**
     * Valida um campo específico.
     * 
     * @param field O campo sendo validado
     * @param value O valor do campo
     * @param fieldPath O caminho completo do campo (ex: "cliente.nome", "cliente.clientePf.cpf")
     * @return Resultado da validação
     */
    ValidationResult validate(Field field, Object value, String fieldPath);
} 