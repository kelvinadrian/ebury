package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteDataDeDesativacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class ClienteDataDeDesativacaoValidator implements FieldValidator {

    private static final String MENSAGEM_DATA_INVALIDA = "Data de desativação inválida";
    private static final String FORMATO_DATA = "ddMMyyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATO_DATA);

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteDataDeDesativacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String dataDeDesativacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(dataDeDesativacao);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo dataDeDesativacao está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar formato da data
        try {
            LocalDate.parse(dataDeDesativacao, FORMATTER);
            log.debug("Campo dataDeDesativacao '{}' é válido", dataDeDesativacao);
            return ValidationResult.valid();
        } catch (DateTimeParseException e) {
            log.debug("Data de desativação '{}' é inválida", dataDeDesativacao);
            return ValidationResult.invalid(
                String.format("%s. Formato esperado: %s. Valor recebido: '%s'", 
                    MENSAGEM_DATA_INVALIDA, FORMATO_DATA, dataDeDesativacao), 
                fieldPath, 
                7
            );
        }
    }
} 