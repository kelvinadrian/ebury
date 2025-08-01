package com.ebury.cadastrocliente.validation.validators;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoNome;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Validador para campos anotados com @ValidacaoNome.
 */
@Component
public class NomeValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        ValidacaoNome annotation = field.getAnnotation(ValidacaoNome.class);
        if (annotation == null) {
            return ValidationResult.valid();
        }
        
        if (value == null) {
            return ValidationResult.invalid("Nome é obrigatório", fieldPath);
        }
        
        if (!(value instanceof String)) {
            return ValidationResult.invalid("Nome deve ser uma string", fieldPath);
        }
        
        String nome = (String) value;
        
        if (nome.trim().isEmpty()) {
            return ValidationResult.invalid("Nome não pode estar vazio", fieldPath);
        }
        
        if (nome.length() < 2) {
            return ValidationResult.invalid("Nome deve ter pelo menos 2 caracteres", fieldPath);
        }
        
        if (nome.length() > 100) {
            return ValidationResult.invalid("Nome deve ter no máximo 100 caracteres", fieldPath);
        }
        
        // Verifica se contém apenas letras, espaços e caracteres especiais comuns
        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s'-]+$")) {
            return ValidationResult.invalid("Nome contém caracteres inválidos", fieldPath);
        }
        
        return ValidationResult.valid();
    }
} 