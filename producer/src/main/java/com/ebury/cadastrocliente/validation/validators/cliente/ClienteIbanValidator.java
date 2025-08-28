package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteIban;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteIbanValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_INVALIDO = "Campo 'iban' não deve ter mais que 30 caracteres";
    private static final int TAMANHO_MAXIMO = 30;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteIban.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String iban = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(iban);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo iban está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (iban.length() > TAMANHO_MAXIMO) {
            log.debug("Campo iban '{}' excede o tamanho máximo de {} caracteres", iban, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_INVALIDO, iban.length()), 
                fieldPath, 
                20
            );
        }

        log.debug("Campo iban '{}' é válido", iban);
        return ValidationResult.valid();
    }
} 