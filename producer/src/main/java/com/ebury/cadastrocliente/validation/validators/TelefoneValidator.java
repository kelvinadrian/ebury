package com.ebury.cadastrocliente.validation.validators;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoTelefone;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Validador para campos anotados com @ValidacaoTelefone.
 */
@Component
public class TelefoneValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        ValidacaoTelefone annotation = field.getAnnotation(ValidacaoTelefone.class);
        if (annotation == null) {
            return ValidationResult.valid();
        }
        
        if (value == null) {
            return ValidationResult.valid(); // Telefone pode ser nulo (opcional)
        }
        
        if (!(value instanceof Integer)) {
            return ValidationResult.invalid("Telefone deve ser um número", fieldPath);
        }
        
        Integer telefone = (Integer) value;
        String telefoneStr = String.valueOf(telefone);
        
        // Telefone brasileiro deve ter 10 ou 11 dígitos
        if (telefoneStr.length() < 10 || telefoneStr.length() > 11) {
            return ValidationResult.invalid("Telefone deve ter 10 ou 11 dígitos", fieldPath);
        }
        
        // Verifica se começa com dígito válido (1-9)
        if (!telefoneStr.matches("^[1-9]\\d{9,10}$")) {
            return ValidationResult.invalid("Telefone deve começar com dígito válido (1-9)", fieldPath);
        }
        
        // Verifica se não é um número muito pequeno ou muito grande
        if (telefone < 1000000000L || telefone > 99999999999L) {
            return ValidationResult.invalid("Telefone deve ter valor válido", fieldPath);
        }
        
        return ValidationResult.valid();
    }
} 