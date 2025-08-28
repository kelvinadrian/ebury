package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteGerenteAnalistaOriginador;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteGerenteAnalistaOriginadorValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'gerenteAnalistaOriginador' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteGerenteAnalistaOriginador.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String gerenteAnalistaOriginador = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(gerenteAnalistaOriginador);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo gerenteAnalistaOriginador está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (gerenteAnalistaOriginador.length() > TAMANHO_MAXIMO) {
            log.debug("Campo gerenteAnalistaOriginador '{}' excede o tamanho máximo de {} caracteres", gerenteAnalistaOriginador, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, gerenteAnalistaOriginador.length()), 
                fieldPath, 
                17
            );
        }

        log.debug("Campo gerenteAnalistaOriginador '{}' é válido", gerenteAnalistaOriginador);
        return ValidationResult.valid();
    }
} 