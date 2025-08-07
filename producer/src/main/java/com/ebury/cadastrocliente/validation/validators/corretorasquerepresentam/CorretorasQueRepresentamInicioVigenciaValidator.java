package com.ebury.cadastrocliente.validation.validators.corretorasquerepresentam;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.corretorasquerepresentam.ValidacaoCorretorasQueRepresentamInicioVigencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class CorretorasQueRepresentamInicioVigenciaValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoCorretorasQueRepresentamInicioVigencia.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para inicioVigencia do CorretoraQueRepresentaDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 