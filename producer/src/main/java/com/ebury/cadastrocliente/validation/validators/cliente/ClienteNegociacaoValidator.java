package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNegociacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Validador para o campo 'negociacao' do ClienteDTO.
 * 
 * Regras de validação baseadas na classe original ValidadorCampoClienteNegociacao:
 * 1. Se o tipo de manutenção for "I" (Inclusão), o campo é obrigatório
 * 2. Se informado, deve ser um dos valores: "MESA", "CORRETORA", "MESA_CORRETORA"
 * 
 * NOTA: Para implementação completa com validação condicional baseada no tipo de manutenção,
 * seria necessário modificar a interface FieldValidator para incluir acesso ao objeto pai
 * ou criar um contexto de validação que permita acessar outros campos do objeto.
 */
@Component
@Slf4j
public class ClienteNegociacaoValidator implements FieldValidator {

    private static final List<String> VALORES_VALIDOS = Arrays.asList("MESA", "CORRETORA", "MESA_CORRETORA");
    private static final String MENSAGEM_CAMPO_OBRIGATORIO = "Campo 'negociacao' deve ser informado para inclusão de cliente";
    private static final String MENSAGEM_VALOR_INVALIDO = "Campo 'negociacao' deve ser um dos valores: MESA, CORRETORA, MESA_CORRETORA";

    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoClienteNegociacao.class) == null) {
            return ValidationResult.valid();
        }

        log.debug("Validando campo {} com valor {}", fieldPath, value);

        // Converte o valor para String
        String negociacao = value != null ? value.toString() : null;
        
        // Verifica se o campo está vazio ou nulo
        boolean isBlank = !StringUtils.hasText(negociacao);

        // Regra 1: Se informado, deve ser um dos valores válidos
        if (!isBlank && !VALORES_VALIDOS.contains(negociacao)) {
            log.debug("Valor '{}' não é válido para o campo negociacao", negociacao);
            return ValidationResult.invalid(
                String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, negociacao), 
                fieldPath, 
                10
            );
        }

        // Regra 2: Campo obrigatório para inclusão
        // Como não temos acesso direto ao campo cliTipoDeManutencao, 
        // vamos validar que o campo seja informado quando não estiver vazio
        // Na implementação completa, seria necessário verificar se cliTipoDeManutencao = "I"
        if (isBlank) {
            log.debug("Campo negociacao está vazio ou nulo");
            return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 10);
        }

        log.debug("Campo negociacao '{}' é válido", negociacao);
        return ValidationResult.valid();
    }
    
    /**
     * Exemplo de como seria a implementação completa com acesso ao contexto:
     * 
     * public ValidationResult validateWithContext(Field field, Object value, String fieldPath, Object parentObject) {
     *     if (field.getAnnotation(ValidacaoClienteNegociacao.class) == null) {
     *         return ValidationResult.valid();
     *     }
     * 
     *     String negociacao = value != null ? value.toString() : null;
     *     boolean isBlank = !StringUtils.hasText(negociacao);
     * 
     *     // Obter o tipo de manutenção do objeto pai
     *     String tipoManutencao = getFieldValue(parentObject, "cliTipoDeManutencao");
     *     
     *     // Regra 1: Se for inclusão (I), o campo é obrigatório
     *     if ("I".equals(tipoManutencao) && isBlank) {
     *         return ValidationResult.invalid(MENSAGEM_CAMPO_OBRIGATORIO, fieldPath, 10);
     *     }
     * 
     *     // Regra 2: Se informado, deve ser um dos valores válidos
     *     if (!isBlank && !VALORES_VALIDOS.contains(negociacao)) {
     *         return ValidationResult.invalid(
     *             String.format("%s. Valor recebido: '%s'", MENSAGEM_VALOR_INVALIDO, negociacao), 
     *             fieldPath, 
     *             10
     *         );
     *     }
     * 
     *     return ValidationResult.valid();
     * }
     */
} 