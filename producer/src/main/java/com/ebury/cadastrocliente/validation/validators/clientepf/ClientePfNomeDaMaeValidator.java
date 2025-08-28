package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfNomeDaMae;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfNomeDaMaeValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'nomeDaMae' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfNomeDaMae.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo nomeDaMae é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String nomeDaMae = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(nomeDaMae);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo nomeDaMae está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (nomeDaMae.length() > TAMANHO_MAXIMO) {
            log.debug("Campo nomeDaMae '{}' excede o tamanho máximo de {} caracteres", nomeDaMae, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, nomeDaMae.length()), 
                fieldPath, 
                29
            );
        }

        log.debug("Campo nomeDaMae '{}' é válido", nomeDaMae);
        return ValidationResult.valid();
    }
} 