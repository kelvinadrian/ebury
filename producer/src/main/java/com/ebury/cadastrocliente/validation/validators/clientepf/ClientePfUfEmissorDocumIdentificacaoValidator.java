package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfUfEmissorDocumIdentificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePfUfEmissorDocumIdentificacaoValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList(
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", 
        "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'ufEmissorDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'ufEmissorDocumIdentificacao' deve ser uma UF válida do Brasil";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfUfEmissorDocumIdentificacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String ufEmissorDocumIdentificacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(ufEmissorDocumIdentificacao);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo ufEmissorDocumIdentificacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 28);
        }

        // Regra 2: Se informado, deve ser uma UF válida
        if (!VALORES_VALIDOS.contains(ufEmissorDocumIdentificacao)) {
            log.debug("Valor '{}' não é válido para o campo ufEmissorDocumIdentificacao", ufEmissorDocumIdentificacao);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, ufEmissorDocumIdentificacao), 
                fieldPath, 
                28
            );
        }

        log.debug("Campo ufEmissorDocumIdentificacao '{}' é válido", ufEmissorDocumIdentificacao);
        return ValidationResult.valid();
    }
} 