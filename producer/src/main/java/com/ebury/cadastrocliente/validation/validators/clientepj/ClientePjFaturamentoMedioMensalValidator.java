package com.ebury.cadastrocliente.validation.validators.clientepj;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepj.ValidacaoClientePjFaturamentoMedioMensal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Component
@Slf4j
public class ClientePjFaturamentoMedioMensalValidator implements FieldValidator {

    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'faturamentoMedioMensal' deve ser um valor numérico válido";
    private static final String MENSAGEM_VALOR_NEGATIVO = "Campo 'faturamentoMedioMensal' não pode ser negativo";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePjFaturamentoMedioMensal.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo faturamentoMedioMensal é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Se informado, deve ser um valor numérico válido e não negativo
        try {
            BigDecimal faturamento;
            if (value instanceof BigDecimal) {
                faturamento = (BigDecimal) value;
            } else if (value instanceof Number) {
                faturamento = new BigDecimal(value.toString());
            } else {
                faturamento = new BigDecimal(value.toString());
            }

            if (faturamento.compareTo(BigDecimal.ZERO) < 0) {
                log.debug("Campo faturamentoMedioMensal '{}' é negativo", faturamento);
                return ValidationResult.invalid(MENSAGEM_VALOR_NEGATIVO, fieldPath, 32);
            }

            log.debug("Campo faturamentoMedioMensal '{}' é válido", faturamento);
            return ValidationResult.valid();
        } catch (NumberFormatException e) {
            log.debug("Campo faturamentoMedioMensal '{}' não é um valor numérico válido", value);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, value), 
                fieldPath, 
                32
            );
        }
    }
} 