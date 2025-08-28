package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfNacionalidade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfNacionalidadeValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'nacionalidade' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfNacionalidade.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo nacionalidade é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String nacionalidade = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(nacionalidade);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo nacionalidade está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (nacionalidade.length() > TAMANHO_MAXIMO) {
            log.debug("Campo nacionalidade '{}' excede o tamanho máximo de {} caracteres", nacionalidade, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, nacionalidade.length()), 
                fieldPath, 
                32
            );
        }

        log.debug("Campo nacionalidade '{}' é válido", nacionalidade);
        return ValidationResult.valid();
    }
} 