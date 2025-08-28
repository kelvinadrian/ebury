package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClientePep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClientePepValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("S", "N");
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'pep' deve ser S ou N";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClientePep.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo pep é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String pep = value.toString();

        // Regra: Se informado, deve ser S ou N
        if (!VALORES_VALIDOS.contains(pep)) {
            log.debug("Valor '{}' não é válido para o campo pep", pep);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, pep), 
                fieldPath, 
                18
            );
        }

        log.debug("Campo pep '{}' é válido", pep);
        return ValidationResult.valid();
    }
} 