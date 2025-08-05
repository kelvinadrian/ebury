package com.ebury.cadastrocliente.validation.validators.representantelegal;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoTipoOperacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class RepresentanteLegalEndTipoDeLogradouroValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoTipoOperacao.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para endTipoDeLogradouro do RepresentanteLegalDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 