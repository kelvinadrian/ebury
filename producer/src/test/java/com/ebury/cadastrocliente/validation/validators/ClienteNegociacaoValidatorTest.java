package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNegociacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste para o ClienteNegociacaoValidator.
 * Demonstra as regras de validação implementadas baseadas na classe original.
 */
class ClienteNegociacaoValidatorTest {

    private ClienteNegociacaoValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteNegociacaoValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteNegociacao(ordem = 10)
            private String negociacao;
        }
        
        field = TestClass.class.getDeclaredField("negociacao");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.negociacao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'negociacao' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.negociacao", result.getFieldPath());
        assertEquals(10, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.negociacao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'negociacao' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.negociacao", result.getFieldPath());
        assertEquals(10, result.getOrdem());
    }

    @Test
    void testValidacaoCampoComEspacos() {
        ValidationResult result = validator.validate(field, "   ", "cliente.negociacao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'negociacao' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.negociacao", result.getFieldPath());
        assertEquals(10, result.getOrdem());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, "INVALIDO", "cliente.negociacao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'negociacao' deve ser um dos valores: MESA, CORRETORA, MESA_CORRETORA"));
        assertTrue(result.getMessage().contains("Valor recebido: 'INVALIDO'"));
        assertEquals("cliente.negociacao", result.getFieldPath());
        assertEquals(10, result.getOrdem());
    }

    @Test
    void testValidacaoValorMesa() {
        ValidationResult result = validator.validate(field, "MESA", "cliente.negociacao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCorretora() {
        ValidationResult result = validator.validate(field, "CORRETORA", "cliente.negociacao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorMesaCorretora() {
        ValidationResult result = validator.validate(field, "MESA_CORRETORA", "cliente.negociacao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCaseInsensitive() {
        // Testa que a validação é case-sensitive (como na implementação original)
        ValidationResult result = validator.validate(field, "mesa", "cliente.negociacao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Valor recebido: 'mesa'"));
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String negociacao;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("negociacao");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.negociacao");
        
        assertTrue(result.isValid());
    }
}
