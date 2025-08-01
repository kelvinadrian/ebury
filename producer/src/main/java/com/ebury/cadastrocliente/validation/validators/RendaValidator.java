package com.ebury.cadastrocliente.validation.validators;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoRenda;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * Validador para campos anotados com @ValidacaoRenda.
 */
@Component
public class RendaValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        ValidacaoRenda annotation = field.getAnnotation(ValidacaoRenda.class);
        if (annotation == null) {
            return ValidationResult.valid();
        }
        
        if (value == null) {
            return ValidationResult.valid(); // Renda pode ser nula (opcional)
        }
        
        if (!(value instanceof Integer)) {
            return ValidationResult.invalid("Renda deve ser um número", fieldPath);
        }
        
        Integer renda = (Integer) value;
        double valorMinimo = annotation.valorMinimo();
        double valorMaximo = annotation.valorMaximo();
        
        // Renda não pode ser negativa
        if (renda < 0) {
            return ValidationResult.invalid("Renda não pode ser negativa", fieldPath);
        }
        
        // Renda não pode ser muito alta
        if (renda > valorMaximo) {
            return ValidationResult.invalid("Renda não pode ser muito alta", fieldPath);
        }
        
        // Renda deve ser um valor razoável se informada
        if (renda > 0 && renda < valorMinimo) {
            return ValidationResult.invalid("Renda deve ser pelo menos R$ " + valorMinimo + ",00", fieldPath);
        }
        
        return ValidationResult.valid();
    }
} 