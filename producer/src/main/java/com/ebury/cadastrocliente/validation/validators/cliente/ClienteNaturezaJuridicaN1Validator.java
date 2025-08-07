package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNaturezaJuridicaN1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteNaturezaJuridicaN1Validator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteNaturezaJuridicaN1.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para naturezaJuridicaN1 do ClienteDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 