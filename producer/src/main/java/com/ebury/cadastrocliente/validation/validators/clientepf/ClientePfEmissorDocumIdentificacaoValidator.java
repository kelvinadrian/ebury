package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfEmissorDocumIdentificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfEmissorDocumIdentificacaoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'emissorDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'emissorDocumIdentificacao' não deve ter mais que 50 caracteres";
    private static final int TAMANHO_MAXIMO = 50;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfEmissorDocumIdentificacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String emissorDocumIdentificacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(emissorDocumIdentificacao);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo emissorDocumIdentificacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 27);
        }

        // Regra 2: Validar tamanho máximo
        if (emissorDocumIdentificacao.length() > TAMANHO_MAXIMO) {
            log.debug("Campo emissorDocumIdentificacao '{}' excede o tamanho máximo de {} caracteres", emissorDocumIdentificacao, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, emissorDocumIdentificacao.length()), 
                fieldPath, 
                27
            );
        }

        log.debug("Campo emissorDocumIdentificacao '{}' é válido", emissorDocumIdentificacao);
        return ValidationResult.valid();
    }
} 