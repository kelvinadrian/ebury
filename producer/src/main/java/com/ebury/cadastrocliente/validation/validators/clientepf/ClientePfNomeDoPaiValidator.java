package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfNomeDoPai;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfNomeDoPaiValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'nomeDoPai' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfNomeDoPai.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo nomeDoPai é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String nomeDoPai = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(nomeDoPai);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo nomeDoPai está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (nomeDoPai.length() > TAMANHO_MAXIMO) {
            log.debug("Campo nomeDoPai '{}' excede o tamanho máximo de {} caracteres", nomeDoPai, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, nomeDoPai.length()), 
                fieldPath, 
                30
            );
        }

        log.debug("Campo nomeDoPai '{}' é válido", nomeDoPai);
        return ValidationResult.valid();
    }
} 