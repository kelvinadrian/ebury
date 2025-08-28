package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNaturezaJuridicaN1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteNaturezaJuridicaN1Validator implements FieldValidator {

    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'naturezaJuridicaN1' deve ser um valor válido";
    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'naturezaJuridicaN1' não deve ter mais que 10 caracteres";
    private static final int TAMANHO_MAXIMO = 10;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteNaturezaJuridicaN1.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Se o valor for nulo, não é obrigatório
        if (value == null) {
            log.debug("Campo naturezaJuridicaN1 é nulo, não é obrigatório");
            return ValidationResult.valid();
        }

        // Converte o valor para String
        String naturezaJuridicaN1 = value.toString();
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(naturezaJuridicaN1);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo naturezaJuridicaN1 está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (naturezaJuridicaN1.length() > TAMANHO_MAXIMO) {
            log.debug("Campo naturezaJuridicaN1 '{}' excede o tamanho máximo de {} caracteres", naturezaJuridicaN1, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, naturezaJuridicaN1.length()), 
                fieldPath, 
                12
            );
        }

        log.debug("Campo naturezaJuridicaN1 '{}' é válido", naturezaJuridicaN1);
        return ValidationResult.valid();
    }
} 