package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteComplementoNatureza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteComplementoNaturezaValidatorTest {

    private ClienteComplementoNaturezaValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteComplementoNaturezaValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteComplementoNatureza(ordem = 11)
            private Integer complementoDaNatureza;
        }
        
        field = TestClass.class.getDeclaredField("complementoDaNatureza");
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, 99, "cliente.complementoDaNatureza");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'complementoDaNatureza' deve ser um valor válido da lista de complementos"));
        assertTrue(result.getMessage().contains("Valor recebido: '99'"));
        assertEquals("cliente.complementoDaNatureza", result.getFieldPath());
        assertEquals(11, result.getOrdem());
    }

    @Test
    void testValidacaoValorValido52() {
        ValidationResult result = validator.validate(field, 52, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido00() {
        ValidationResult result = validator.validate(field, 0, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido02() {
        ValidationResult result = validator.validate(field, 2, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido08() {
        ValidationResult result = validator.validate(field, 8, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido09() {
        ValidationResult result = validator.validate(field, 9, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido55() {
        ValidationResult result = validator.validate(field, 55, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorValido87() {
        ValidationResult result = validator.validate(field, 87, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorComoString() {
        ValidationResult result = validator.validate(field, "52", "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private Integer complementoDaNatureza;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("complementoDaNatureza");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, 99, "cliente.complementoDaNatureza");
        
        assertTrue(result.isValid());
    }
}
