package com.ebury.cadastrocliente.validation.validators.contacorrenteteddoc;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.contacorrenteteddoc.ValidacaoContaCorrenteTedDocCodBacenBanco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ContaCorrenteTedDocCodBacenBancoValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoContaCorrenteTedDocCodBacenBanco.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para ccTedCodBacenBanco do ContaCorrenteTedDocDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 