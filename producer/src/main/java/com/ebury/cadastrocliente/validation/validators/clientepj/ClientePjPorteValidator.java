package com.ebury.cadastrocliente.validation.validators.clientepj;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepj.ValidacaoClientePjPorte;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePjPorteValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("MICRO", "PEQUENO", "MEDIO", "GRANDE", "NAO_INFORMADO");
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'porte' deve ser MICRO, PEQUENO, MEDIO, GRANDE ou NAO_INFORMADO";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePjPorte.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo porte é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String porte = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(porte);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo porte está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(porte)) {
            log.debug("Valor '{}' não é válido para o campo porte", porte);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, porte), 
                fieldPath, 
                31
            );
        }

        log.debug("Campo porte '{}' é válido", porte);
        return ValidationResult.valid();
    }
} 