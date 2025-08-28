package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfDescrDocumIdentificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClientePfDescrDocumIdentificacaoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'descrDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'descrDocumIdentificacao' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfDescrDocumIdentificacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String descrDocumIdentificacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(descrDocumIdentificacao);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo descrDocumIdentificacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 26);
        }

        // Regra 2: Validar tamanho máximo
        if (descrDocumIdentificacao.length() > TAMANHO_MAXIMO) {
            log.debug("Campo descrDocumIdentificacao '{}' excede o tamanho máximo de {} caracteres", descrDocumIdentificacao, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, descrDocumIdentificacao.length()), 
                fieldPath, 
                26
            );
        }

        log.debug("Campo descrDocumIdentificacao '{}' é válido", descrDocumIdentificacao);
        return ValidationResult.valid();
    }
}
