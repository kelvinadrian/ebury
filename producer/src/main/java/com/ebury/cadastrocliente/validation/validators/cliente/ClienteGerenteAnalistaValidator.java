package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteGerenteAnalista;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteGerenteAnalistaValidator implements FieldValidator {

    private static final String MENSAGEM_TAMANHO_MAXIMO = "Campo 'gerenteAnalista' não deve ter mais que 100 caracteres";
    private static final int TAMANHO_MAXIMO = 100;

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteGerenteAnalista.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String gerenteAnalista = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(gerenteAnalista);

        // Se estiver vazio, não é obrigatório
        if (isBlank) {
            log.debug("Campo gerenteAnalista está vazio, não é obrigatório");
            return ValidationResult.valid();
        }

        // Regra: Validar tamanho máximo
        if (gerenteAnalista.length() > TAMANHO_MAXIMO) {
            log.debug("Campo gerenteAnalista '{}' excede o tamanho máximo de {} caracteres", gerenteAnalista, TAMANHO_MAXIMO);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido tem %d caracteres", MENSAGEM_TAMANHO_MAXIMO, gerenteAnalista.length()), 
                fieldPath, 
                16
            );
        }

        log.debug("Campo gerenteAnalista '{}' é válido", gerenteAnalista);
        return ValidationResult.valid();
    }
} 