package com.ebury.cadastrocliente.validation.validators.clientepj;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepj.ValidacaoClientePjUfEmissorInscricaoEstadual;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePjUfEmissorInscricaoEstadualValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList(
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", 
        "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    );
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'ufEmissorInscricaoEstadual' deve ser uma UF válida do Brasil";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePjUfEmissorInscricaoEstadual.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo ufEmissorInscricaoEstadual é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String ufEmissorInscricaoEstadual = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(ufEmissorInscricaoEstadual);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo ufEmissorInscricaoEstadual está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Se informado, deve ser uma UF válida
        if (!VALORES_VALIDOS.contains(ufEmissorInscricaoEstadual)) {
            log.debug("Valor '{}' não é válido para o campo ufEmissorInscricaoEstadual", ufEmissorInscricaoEstadual);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, ufEmissorInscricaoEstadual), 
                fieldPath, 
                30
            );
        }

        log.debug("Campo ufEmissorInscricaoEstadual '{}' é válido", ufEmissorInscricaoEstadual);
        return ValidationResult.valid();
    }
} 