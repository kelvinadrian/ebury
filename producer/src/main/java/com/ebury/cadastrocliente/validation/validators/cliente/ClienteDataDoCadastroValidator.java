package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteDataDoCadastro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class ClienteDataDoCadastroValidator implements FieldValidator {

    private static final String MENSAGEM_DATA_INVALIDA = "Data do cadastro inválida";
    private static final String FORMATO_DATA = "ddMMyyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATO_DATA);

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteDataDoCadastro.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String dataCadastro = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(dataCadastro);

        // Se estiver vazio, não é obrigatório (usa data atual)
        if (isBlank) {
            log.debug("Campo dataDoCadastro está vazio, será usado a data atual");
            return ValidationResult.valid();
        }

        // Regra: Validar formato da data
        try {
            LocalDate.parse(dataCadastro, FORMATTER);
            log.debug("Campo dataDoCadastro '{}' é válido", dataCadastro);
            return ValidationResult.valid();
        } catch (DateTimeParseException e) {
            log.debug("Data do cadastro '{}' é inválida", dataCadastro);
            return ValidationResult.invalid(
                String.format("%s. Formato esperado: %s. Valor recebido: '%s'", 
                    MENSAGEM_DATA_INVALIDA, FORMATO_DATA, dataCadastro), 
                fieldPath, 
                6
            );
        }
    }
} 