package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfUfDaNaturalidade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePfUfDaNaturalidadeValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList(
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", 
        "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'ufDaNaturalidade' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'ufDaNaturalidade' deve ser uma UF válida do Brasil";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfUfDaNaturalidade.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String ufDaNaturalidade = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(ufDaNaturalidade);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo ufDaNaturalidade está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 33);
        }

        // Regra 2: Se informado, deve ser uma UF válida
        if (!VALORES_VALIDOS.contains(ufDaNaturalidade)) {
            log.debug("Valor '{}' não é válido para o campo ufDaNaturalidade", ufDaNaturalidade);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, ufDaNaturalidade), 
                fieldPath, 
                33
            );
        }

        log.debug("Campo ufDaNaturalidade '{}' é válido", ufDaNaturalidade);
        return ValidationResult.valid();
    }
} 