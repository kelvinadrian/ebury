package com.ebury.cadastrocliente.validation.validators.contacorrente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.contacorrente.ValidacaoContaCorrentePreferencial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ContaCorrentePreferencialValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoContaCorrentePreferencial.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para ccPreferencial do ContaCorrenteDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 