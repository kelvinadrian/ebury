package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteCodigoCorporativo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteCodigoCorporativoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'codCorporativo' deve ser informado quando outros campos de consulta não estão preenchidos";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'codCorporativo' não deve ter mais que 50 caracteres";
    private static final int TAMANHO_MAXIMO = 50;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteCodigoCorporativo.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String codCorporativo = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(codCorporativo);

        // Regra 1: Campo obrigatório quando outros campos de consulta não estão preenchidos
        // Como não temos acesso direto aos outros campos, vamos validar apenas se o valor for informado
        if (isBlank) {
            log.debug("Campo codCorporativo está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 4);
        }

        // Regra 2: Validar tamanho máximo
        if (codCorporativo.length() > TAMANHO_MAXIMO) {
            log.debug("Campo codCorporativo '{}' excede o tamanho máximo de {} caracteres", codCorporativo, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, codCorporativo.length()), 
                fieldPath, 
                4
            );
        }

        log.debug("Campo codCorporativo '{}' é válido", codCorporativo);
        return ValidationResult.valid();
    }
} 