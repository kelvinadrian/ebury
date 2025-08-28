package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteNomeValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'nome' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'nome' não deve ter mais que 250 caracteres";
    private static final int TAMANHO_MAXIMO = 250;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteNome.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String nome = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(nome);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo nome está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 5);
        }

        // Regra 2: Validar tamanho máximo
        if (!isBlank && nome.length() > TAMANHO_MAXIMO) {
            log.debug("Campo nome '{}' excede o tamanho máximo de {} caracteres", nome, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, nome.length()), 
                fieldPath, 
                5
            );
        }

        log.debug("Campo nome '{}' é válido", nome);
        return ValidationResult.valid();
    }
} 