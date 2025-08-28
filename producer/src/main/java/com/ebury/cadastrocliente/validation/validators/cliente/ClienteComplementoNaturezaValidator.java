package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteComplementoNatureza;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteComplementoNaturezaValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList(
        "52", "00", "02", "08", "09", "55", "56", "58", "59", "61", 
        "62", "66", "71", "78", "79", "81", "84", "87"
    );
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'complementoDaNatureza' deve ser um valor válido da lista de complementos";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteComplementoNatureza.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo complementoDaNatureza é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String com formatação adequada
        String complementoDaNatureza;
        if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            if (intValue < 10) {
                complementoDaNatureza = "0" + intValue.toString();
            } else {
                complementoDaNatureza = intValue.toString();
            }
        } else {
            complementoDaNatureza = value.toString();
        }

        // Regra: Se informado, deve ser um valor válido da lista
        if (!VALORES_VALIDOS.contains(complementoDaNatureza)) {
            log.debug("Valor '{}' não é válido para o campo complementoDaNatureza", complementoDaNatureza);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, complementoDaNatureza), 
                fieldPath, 
                11
            );
        }

        log.debug("Campo complementoDaNatureza '{}' é válido", complementoDaNatureza);
        return ValidationResult.valid();
    }
} 