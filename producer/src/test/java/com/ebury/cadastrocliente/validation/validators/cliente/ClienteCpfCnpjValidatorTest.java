package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteCpfCnpj;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteCpfCnpjValidatorTest {

    private ClienteCpfCnpjValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteCpfCnpjValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteCpfCnpj(ordem = 3)
            private String cpfCnpj;
        }
        
        field = TestClass.class.getDeclaredField("cpfCnpj");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.cpfCnpj");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'cpfCnpj' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.cpfCnpj", result.getFieldPath());
        assertEquals(3, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.cpfCnpj");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'cpfCnpj' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.cpfCnpj", result.getFieldPath());
        assertEquals(3, result.getOrdem());
    }

    @Test
    void testValidacaoCpfValido() {
        ValidationResult result = validator.validate(field, "12345678909", "cliente.cpfCnpj");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoCpfInvalido() {
        ValidationResult result = validator.validate(field, "12345678900", "cliente.cpfCnpj");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("CPF inválido"));
        assertEquals("cliente.cpfCnpj", result.getFieldPath());
        assertEquals(3, result.getOrdem());
    }

    @Test
    void testValidacaoCnpjValido() {
        ValidationResult result = validator.validate(field, "12345678000195", "cliente.cpfCnpj");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoCnpjInvalido() {
        ValidationResult result = validator.validate(field, "12345678000100", "cliente.cpfCnpj");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("CNPJ inválido"));
        assertEquals("cliente.cpfCnpj", result.getFieldPath());
        assertEquals(3, result.getOrdem());
    }

    @Test
    void testValidacaoFormatoInvalido() {
        ValidationResult result = validator.validate(field, "123456789", "cliente.cpfCnpj");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Formato inválido para CPF/CNPJ"));
        assertEquals("cliente.cpfCnpj", result.getFieldPath());
        assertEquals(3, result.getOrdem());
    }

    @Test
    void testValidacaoCpfComMascara() {
        ValidationResult result = validator.validate(field, "123.456.789-09", "cliente.cpfCnpj");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoCnpjComMascara() {
        ValidationResult result = validator.validate(field, "12.345.678/0001-95", "cliente.cpfCnpj");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String cpfCnpj;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("cpfCnpj");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.cpfCnpj");
        
        assertTrue(result.isValid());
    }
}
