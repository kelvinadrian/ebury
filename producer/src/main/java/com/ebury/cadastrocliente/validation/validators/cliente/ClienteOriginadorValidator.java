package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteOriginador;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteOriginadorValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'originador' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'originador' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteOriginador.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String originador = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(originador);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo originador está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 14);
        }

        // Regra 2: Validar tamanho máximo
        if (originador.length() > TAMANHO_MAXIMO) {
            log.debug("Campo originador '{}' excede o tamanho máximo de {} caracteres", originador, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, originador.length()), 
                fieldPath, 
                14
            );
        }

        log.debug("Campo originador '{}' é válido", originador);
        return ValidationResult.valid();
    }
} 