package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteTipoDePessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTipoDePessoaValidatorTest {

    private ClienteTipoDePessoaValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteTipoDePessoaValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteTipoDePessoa(ordem = 2)
            private String tipoDePessoa;
        }
        
        field = TestClass.class.getDeclaredField("tipoDePessoa");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.tipoDePessoa");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'tipoDePessoa' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.tipoDePessoa", result.getFieldPath());
        assertEquals(2, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.tipoDePessoa");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'tipoDePessoa' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.tipoDePessoa", result.getFieldPath());
        assertEquals(2, result.getOrdem());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, "INVALIDO", "cliente.tipoDePessoa");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'tipoDePessoa' deve ser PF ou PJ"));
        assertTrue(result.getMessage().contains("Valor recebido: 'INVALIDO'"));
        assertEquals("cliente.tipoDePessoa", result.getFieldPath());
        assertEquals(2, result.getOrdem());
    }

    @Test
    void testValidacaoValorPF() {
        ValidationResult result = validator.validate(field, "PF", "cliente.tipoDePessoa");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorPJ() {
        ValidationResult result = validator.validate(field, "PJ", "cliente.tipoDePessoa");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCaseInsensitive() {
        // Testa que a validação é case-sensitive
        ValidationResult result = validator.validate(field, "pf", "cliente.tipoDePessoa");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Valor recebido: 'pf'"));
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String tipoDePessoa;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("tipoDePessoa");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.tipoDePessoa");
        
        assertTrue(result.isValid());
    }
}
