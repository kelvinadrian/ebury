package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteTipoDeManutencao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteTipoDeManutencaoValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("I", "A", "E");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'cliTipoDeManutencao' deve ser informado";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'cliTipoDeManutencao' deve ser I (Inclusão), A (Alteração) ou E (Exclusão)";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteTipoDeManutencao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String cliTipoDeManutencao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(cliTipoDeManutencao);

        // Regra 1: Campo obrigatório
        if (isBlank) {
            log.debug("Campo cliTipoDeManutencao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 1);
        }

        // Regra 2: Se informado, deve ser um dos valores válidos
        if (!VALORES_VALIDOS.contains(cliTipoDeManutencao)) {
            log.debug("Valor '{}' não é válido para o campo cliTipoDeManutencao", cliTipoDeManutencao);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, cliTipoDeManutencao), 
                fieldPath, 
                1
            );
        }

        log.debug("Campo cliTipoDeManutencao '{}' é válido", cliTipoDeManutencao);
        return ValidationResult.valid();
    }
} 