package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteCpfCnpj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Component
@Slf4j
public class ClienteCpfCnpjValidator implements FieldValidator {

    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'cpfCnpj' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_CPF_INVALIDO = "CPF inválido";
    private static final String MENSAGEM_CNPJ_INVALIDO = "CNPJ inválido";
    private static final String MENSAGEM_FORMATO_INVALIDO = "Formato inválido para CPF/CNPJ";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteCpfCnpj.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String cpfCnpj = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(cpfCnpj);

        // Regra 1: Campo obrigatório para inclusão
        if (isBlank) {
            log.debug("Campo cpfCnpj está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 3);
        }

        // Regra 2: Validar formato e dígitos verificadores
        if (!isBlank) {
            String cpfCnpjLimpo = cpfCnpj.replaceAll("[^0-9]", "");
            
            if (cpfCnpjLimpo.length() == 11) {
                // Validar CPF
                if (!validarCpf(cpfCnpjLimpo)) {
                    log.debug("CPF '{}' é inválido", cpfCnpj);
                    return ValidationResult.invalid(
                        String.format("%s. Valor recebido: '%s'", MENSAGEM_CPF_INVALIDO, cpfCnpj), 
                        fieldPath, 
                        3
                    );
                }
            } else if (cpfCnpjLimpo.length() == 14) {
                // Validar CNPJ
                if (!validarCnpj(cpfCnpjLimpo)) {
                    log.debug("CNPJ '{}' é inválido", cpfCnpj);
                    return ValidationResult.invalid(
                        String.format("%s. Valor recebido: '%s'", MENSAGEM_CNPJ_INVALIDO, cpfCnpj), 
                        fieldPath, 
                        3
                    );
                }
            } else {
                log.debug("Formato inválido para CPF/CNPJ: '{}'", cpfCnpj);
                return ValidationResult.invalid(
                    String.format("%s. Valor recebido: '%s'", MENSAGEM_FORMATO_INVALIDO, cpfCnpj), 
                    fieldPath, 
                    3
                );
            }
        }

        log.debug("Campo cpfCnpj '{}' é válido", cpfCnpj);
        return ValidationResult.valid();
    }

    /**
     * Valida CPF
     */
    private boolean validarCpf(String cpf) {
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int digito1 = resto > 9 ? 0 : resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = 11 - (soma % 11);
        int digito2 = resto > 9 ? 0 : resto;

        return Character.getNumericValue(cpf.charAt(9)) == digito1 && 
               Character.getNumericValue(cpf.charAt(10)) == digito2;
    }

    /**
     * Valida CNPJ
     */
    private boolean validarCnpj(String cnpj) {
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos1[i];
        }
        int resto = soma % 11;
        int digito1 = resto < 2 ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
        }
        resto = soma % 11;
        int digito2 = resto < 2 ? 0 : 11 - resto;

        return Character.getNumericValue(cnpj.charAt(12)) == digito1 && 
               Character.getNumericValue(cnpj.charAt(13)) == digito2;
    }
} 