package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfNomeDoConjuge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfNomeDoConjugeValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'nomeDoConjuge' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfNomeDoConjuge.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo nomeDoConjuge é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String nomeDoConjuge = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(nomeDoConjuge);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo nomeDoConjuge está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (nomeDoConjuge.length() > TAMANHO_MAXIMO) {
            log.debug("Campo nomeDoConjuge '{}' excede o tamanho máximo de {} caracteres", nomeDoConjuge, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, nomeDoConjuge.length()), 
                fieldPath, 
                31
            );
        }

        log.debug("Campo nomeDoConjuge '{}' é válido", nomeDoConjuge);
        return ValidationResult.valid();
    }
} 