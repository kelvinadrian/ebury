package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfSexo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePfSexoValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("FEMININO", "MASCULINO");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'sexo' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'sexo' deve ser FEMININO ou MASCULINO";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfSexo.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String sexo = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(sexo);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo sexo está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 21);
        }

        // Regra 2: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(sexo)) {
            log.debug("Valor '{}' não é válido para o campo sexo", sexo);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, sexo), 
                fieldPath, 
                21
            );
        }

        log.debug("Campo sexo '{}' é válido", sexo);
        return ValidationResult.valid();
    }
} 