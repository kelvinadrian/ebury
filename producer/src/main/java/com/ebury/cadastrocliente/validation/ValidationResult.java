package com.ebury.cadastrocliente.validation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa o resultado de uma validação de campo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    
    private boolean valid;
    private String message;
    private String fieldPath;
    private int ordem = 100; // Ordem padrão
    
    /**
     * Cria um resultado de validação válido.
     * 
     * @return Resultado válido
     */
    public static ValidationResult valid() {
        return new ValidationResult(true, null, null, 100);
    }
    
    /**
     * Cria um resultado de validação inválido.
     * 
     * @param message Mensagem de erro
     * @param fieldPath Caminho do campo
     * @return Resultado inválido
     */
    public static ValidationResult invalid(String message, String fieldPath) {
        return new ValidationResult(false, message, fieldPath, 100);
    }
    
    /**
     * Cria um resultado de validação inválido.
     * 
     * @param message Mensagem de erro
     * @return Resultado inválido
     */
    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, message, null, 100);
    }
    
    /**
     * Cria um resultado de validação inválido com ordem específica.
     * 
     * @param message Mensagem de erro
     * @param fieldPath Caminho do campo
     * @param ordem Ordem de prioridade
     * @return Resultado inválido
     */
    public static ValidationResult invalid(String message, String fieldPath, int ordem) {
        return new ValidationResult(false, message, fieldPath, ordem);
    }
} 