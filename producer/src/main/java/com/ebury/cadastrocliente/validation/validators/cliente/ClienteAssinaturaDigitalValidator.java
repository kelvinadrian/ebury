package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteAssinaturaDigital;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteAssinaturaDigitalValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("S", "N");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'utilizaAssinaturaDigital' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'utilizaAssinaturaDigital' deve ser S ou N";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteAssinaturaDigital.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String utilizaAssinaturaDigital = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(utilizaAssinaturaDigital);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo utilizaAssinaturaDigital está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 9);
        }

        // Regra 2: Se informado, deve ser S ou N
        if (!VALORES_VALIDOS.contains(utilizaAssinaturaDigital)) {
            log.debug("Valor '{}' não é válido para o campo utilizaAssinaturaDigital", utilizaAssinaturaDigital);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, utilizaAssinaturaDigital), 
                fieldPath, 
                9
            );
        }

        log.debug("Campo utilizaAssinaturaDigital '{}' é válido", utilizaAssinaturaDigital);
        return ValidationResult.valid();
    }
} 