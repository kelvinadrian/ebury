package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteTipoDePessoa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ClienteTipoDePessoaValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("PF", "PJ");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'tipoDePessoa' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'tipoDePessoa' deve ser PF ou PJ";
    private static final String MENSAGEM_CAMPOS_CONSULTA = "Para alteração/exclusão, pelo menos um dos campos (cpfCnpj, codExterno, codCorporativo) deve ser informado";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteTipoDePessoa.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String tipoDePessoa = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(tipoDePessoa);

        // Regra 1: Se informado, deve ser um dos valores válidos
        if (!isBlank && !VALORES_VALIDOS.contains(tipoDePessoa)) {
            log.debug("Valor '{}' não é válido para o campo tipoDePessoa", tipoDePessoa);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, tipoDePessoa), 
                fieldPath, 
                2
            );
        }

        // Regra 2: Campo obrigatório para inclusão
        // Como não temos acesso direto ao campo cliTipoDeManutencao, 
        // vamos validar que o campo seja informado quando não estiver vazio
        if (isBlank) {
            log.debug("Campo tipoDePessoa está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 2);
        }

        log.debug("Campo tipoDePessoa '{}' é válido", tipoDePessoa);
        return ValidationResult.valid();
    }
} 