package com.ebury.cadastrocliente.validation.validators;

import com.ebury.cadastrocliente.validation.FieldValidator;
import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.ValidacaoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class DataValidator implements FieldValidator {
    
    @Override
    public ValidationResult validate(Field field, Object value, String fieldPath) {
        if (field.getAnnotation(ValidacaoData.class) == null) {
            return ValidationResult.valid();
        }
        
        if (value == null || value.toString().trim().isEmpty()) {
            return ValidationResult.valid(); // Campo opcional
        }
        
        String dataStr = value.toString().trim();
        
        try {
            // Tenta parsear a data no formato dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(dataStr, formatter);
            
            return ValidationResult.valid();
            
        } catch (DateTimeParseException e) {
            // Exemplo de mensagem de erro que mostra o caminho completo do campo
            String mensagem = String.format("Data inválida no campo '%s'. Formato esperado: dd/MM/yyyy. Valor recebido: '%s'", 
                                          fieldPath, dataStr);
            
            log.debug("Validação de data falhou para {}: {}", fieldPath, mensagem);
            return ValidationResult.invalid(mensagem, fieldPath);
        }
    }
} 