package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteCodExterno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteCodExternoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'codExterno' deve ser informado quando outros campos de consulta não estão preenchidos";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'codExterno' não deve ter mais que 50 caracteres";
    private static final int TAMANHO_MAXIMO = 50;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteCodExterno.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String codExterno = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(codExterno);

        // Regra 1: Campo obrigatório quando outros campos de consulta não estão preenchidos
        // Como não temos acesso direto aos outros campos, vamos validar apenas se o valor for informado
        if (isBlank) {
            log.debug("Campo codExterno está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 5);
        }

        // Regra 2: Validar tamanho máximo
        if (codExterno.length() > TAMANHO_MAXIMO) {
            log.debug("Campo codExterno '{}' excede o tamanho máximo de {} caracteres", codExterno, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, codExterno.length()), 
                fieldPath, 
                5
            );
        }

        log.debug("Campo codExterno '{}' é válido", codExterno);
        return ValidationResult.valid();
    }
}
