package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfDataDeNascimento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class ClientePfDataDeNascimentoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'dataDeNascimento' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_DATA_INVALIDA = "Data de nascimento inválida";
    private static final String FORMATO_DATA = "ddMMyyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATO_DATA);

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfDataDeNascimento.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String dataDeNascimento = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(dataDeNascimento);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo dataDeNascimento está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 23);
        }

        // Regra 2: Validar formato da data
        try {
            LocalDate.parse(dataDeNascimento, FORMATTER);
            log.debug("Campo dataDeNascimento '{}' é válido", dataDeNascimento);
            return ValidationResult.valid();
        } catch (DateTimeParseException e) {
            log.debug("Data de nascimento '{}' é inválida", dataDeNascimento);
            return ValidationResult.invalid(
                String.format("%s. Formato esperado: %s. Valor recebido: '%s'", 
                    MENSAGEM_DATA_INVALIDA, FORMATO_DATA, dataDeNascimento), 
                fieldPath, 
                23
            );
        }
    }
} 