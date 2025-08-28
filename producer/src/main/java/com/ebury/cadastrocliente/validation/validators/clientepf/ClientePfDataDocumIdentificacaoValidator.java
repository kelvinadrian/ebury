package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfDataDocumIdentificacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class ClientePfDataDocumIdentificacaoValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'dataDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física";
    private static final String MENSAGEM_DATA_INVALIDA = "Data do documento de identificação inválida";
    private static final String FORMATO_DATA = "ddMMyyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATO_DATA);

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePfDataDocumIdentificacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String dataDocumIdentificacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(dataDocumIdentificacao);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo dataDocumIdentificacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 25);
        }

        // Regra 2: Validar formato da data
        try {
            LocalDate.parse(dataDocumIdentificacao, FORMATTER);
            log.debug("Campo dataDocumIdentificacao '{}' é válido", dataDocumIdentificacao);
            return ValidationResult.valid();
        } catch (DateTimeParseException e) {
            log.debug("Data do documento de identificação '{}' é inválida", dataDocumIdentificacao);
            return ValidationResult.invalid(
                String.format("%s. Formato esperado: %s. Valor recebido: '%s'", 
                    MENSAGEM_DATA_INVALIDA, FORMATO_DATA, dataDocumIdentificacao), 
                fieldPath, 
                25
            );
        }
    }
} 