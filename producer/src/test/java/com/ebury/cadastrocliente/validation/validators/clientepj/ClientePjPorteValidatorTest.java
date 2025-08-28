package com.ebury.cadastrocliente.validation.validators.clientepj;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepj.ValidacaoClientePjPorte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClientePjPorteValidatorTest {

    private ClientePjPorteValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClientePjPorteValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClientePjPorte(ordem = 31)
            private String porte;
        }
        
        field = TestClass.class.getDeclaredField("porte");
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, "INVALIDO", "cliente.clientePj.porte");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'porte' deve ser MICRO, PEQUENO, MEDIO, GRANDE ou NAO_INFORMADO"));
        assertTrue(result.getMessage().contains("Valor recebido: 'INVALIDO'"));
        assertEquals("cliente.clientePj.porte", result.getFieldPath());
        assertEquals(31, result.getOrdem());
    }

    @Test
    void testValidacaoValorMicro() {
        ValidationResult result = validator.validate(field, "MICRO", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorPequeno() {
        ValidationResult result = validator.validate(field, "PEQUENO", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorMedio() {
        ValidationResult result = validator.validate(field, "MEDIO", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorGrande() {
        ValidationResult result = validator.validate(field, "GRANDE", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorNaoInformado() {
        ValidationResult result = validator.validate(field, "NAO_INFORMADO", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCaseInsensitive() {
        // Testa que a validação é case-sensitive
        ValidationResult result = validator.validate(field, "micro", "cliente.clientePj.porte");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Valor recebido: 'micro'"));
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String porte;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("porte");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.clientePj.porte");
        
        assertTrue(result.isValid());
    }
}
