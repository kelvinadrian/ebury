package com.ebury.cadastrocliente.validation.validators.endereco;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.endereco.ValidacaoEnderecoTipoDeLogradouro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Slf4j
public class EnderecoTipoDeLogradouroValidator implements FieldValidator {

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoEnderecoTipoDeLogradouro.class) == null) {
            return ValidationResult.valid();
        }

        // TODO: Implementar regras de validação específicas para endTipoDeLogradouro do EnderecoDTO
        log.debug("Validando campo {} com valor {}", fieldPath, value);
        
        return ValidationResult.valid();
    }
} 