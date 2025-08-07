package com.ebury.cadastrocliente.validation.validators.emailsdocumentos;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.emailsdocumentos.ValidacaoEmailsDocumentosEnviarCcPara;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class EmailsDocumentosEnviarCcParaValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoEmailsDocumentosEnviarCcPara.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para enviarCcPara do EmailsDocumentoDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 