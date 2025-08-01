package com.ebury.cadastrocliente.validation.validators;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoEmail;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Validador para campos anotados com @ValidacaoEmail.
 * Exemplo de como criar novos validadores.
 */
@Component
public class EmailValidator implements FieldValidator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        ValidacaoEmail annotation = field.getAnnotation(ValidacaoEmail.class);
        if (annotation == null) {
            return ValidationResult.valid();
        }
        
        if (value == null) {
            return ValidationResult.valid(); // Email pode ser nulo (opcional)
        }
        
        if (!(value instanceof String)) {
            return ValidationResult.invalid("Email deve ser uma string", fieldPath);
        }
        
        String email = (String) value;
        
        if (email.trim().isEmpty()) {
            return ValidationResult.valid(); // Email vazio é válido (opcional)
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ValidationResult.invalid("Formato de email inválido", fieldPath);
        }
        
        return ValidationResult.valid();
    }
} 