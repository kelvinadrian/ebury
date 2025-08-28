package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfEstadoCivil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePfEstadoCivilValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("CASADO", "DIVORCIADO", "SEPARADO_JUDICIALMENTE", "SOLTEIRO", "VIUVO");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'estadoCivil' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'estadoCivil' deve ser CASADO, DIVORCIADO, SEPARADO_JUDICIALMENTE, SOLTEIRO ou VIUVO";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfEstadoCivil.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String estadoCivil = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(estadoCivil);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo estadoCivil está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 22);
        }

        // Regra 2: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(estadoCivil)) {
            log.debug("Valor '{}' não é válido para o campo estadoCivil", estadoCivil);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, estadoCivil), 
                fieldPath, 
                22
            );
        }

        log.debug("Campo estadoCivil '{}' é válido", estadoCivil);
        return ValidationResult.valid();
    }
} 