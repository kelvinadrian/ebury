package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfDocumIdentificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfDocumIdentificacaoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'documIdentificacao' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'documIdentificacao' não deve ter mais que 50 caracteres";
    private static final int TAMANHO_MAXIMO = 50;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfDocumIdentificacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String documIdentificacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(documIdentificacao);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo documIdentificacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 24);
        }

        // Regra 2: Validar tamanho máximo
        if (documIdentificacao.length() > TAMANHO_MAXIMO) {
            log.debug("Campo documIdentificacao '{}' excede o tamanho máximo de {} caracteres", documIdentificacao, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, documIdentificacao.length()), 
                fieldPath, 
                24
            );
        }

        log.debug("Campo documIdentificacao '{}' é válido", documIdentificacao);
        return ValidationResult.valid();
    }
} 