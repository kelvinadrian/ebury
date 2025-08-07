package com.ebury.cadastrocliente.validation.validators.enderecoexterior;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.enderecoexterior.ValidacaoEnderecoExteriorEstado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class EnderecoExteriorEstadoValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoEnderecoExteriorEstado.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para endEstado do EnderecoNoExteriorDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 