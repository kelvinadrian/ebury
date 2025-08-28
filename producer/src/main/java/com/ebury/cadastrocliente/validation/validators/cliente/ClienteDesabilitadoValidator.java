package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteDesabilitado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteDesabilitadoValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("S", "N");
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'desabilitado' deve ser S ou N";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteDesabilitado.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo desabilitado é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String desabilitado = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(desabilitado);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo desabilitado está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Se informado, deve ser S ou N
        if (!VALORES_VALIDOS.contains(desabilitado)) {
            log.debug("Valor '{}' não é válido para o campo desabilitado", desabilitado);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, desabilitado), 
                fieldPath, 
                8
            );
        }

        log.debug("Campo desabilitado '{}' é válido", desabilitado);
        return ValidationResult.valid();
    }
} 