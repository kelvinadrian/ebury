package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteTipoResidencia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteTipoResidenciaValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("FORA_DO_PAIS", "NOPAIS", "MAIS_DE_UM_PAIS");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'tipoDeResidencia' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'tipoDeResidencia' deve ser FORA_DO_PAIS, NOPAIS ou MAIS_DE_UM_PAIS";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteTipoResidencia.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String tipoDeResidencia = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(tipoDeResidencia);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo tipoDeResidencia está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 15);
        }

        // Regra 2: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(tipoDeResidencia)) {
            log.debug("Valor '{}' não é válido para o campo tipoDeResidencia", tipoDeResidencia);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, tipoDeResidencia), 
                fieldPath, 
                15
            );
        }

        log.debug("Campo tipoDeResidencia '{}' é válido", tipoDeResidencia);
        return ValidationResult.valid();
    }
} 