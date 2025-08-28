package com.ebury.cadastrocliente.validation.validators.clientepj;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepj.ValidacaoClientePjRamoDeAtividade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePjRamoDeAtividadeValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U");
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'ramoDeAtividade' deve ser uma letra de A a U";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePjRamoDeAtividade.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo ramoDeAtividade é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String ramoDeAtividade = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(ramoDeAtividade);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo ramoDeAtividade está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(ramoDeAtividade)) {
            log.debug("Valor '{}' não é válido para o campo ramoDeAtividade", ramoDeAtividade);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, ramoDeAtividade), 
                fieldPath, 
                33
            );
        }

        log.debug("Campo ramoDeAtividade '{}' é válido", ramoDeAtividade);
        return ValidationResult.valid();
    }
} 