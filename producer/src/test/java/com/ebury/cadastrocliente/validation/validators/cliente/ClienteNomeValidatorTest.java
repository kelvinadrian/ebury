package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteNome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteNomeValidatorTest {

    private ClienteNomeValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteNomeValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteNome(ordem = 5)
            private String nome;
        }
        
        field = TestClass.class.getDeclaredField("nome");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.nome");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'nome' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.nome", result.getFieldPath());
        assertEquals(5, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.nome");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'nome' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.nome", result.getFieldPath());
        assertEquals(5, result.getOrdem());
    }

    @Test
    void testValidacaoCampoComEspacos() {
        ValidationResult result = validator.validate(field, "   ", "cliente.nome");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'nome' deve ser informado para inclusão de cliente", result.getMessage());
        assertEquals("cliente.nome", result.getFieldPath());
        assertEquals(5, result.getOrdem());
    }

    @Test
    void testValidacaoNomeValido() {
        ValidationResult result = validator.validate(field, "João da Silva", "cliente.nome");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoNomeComTamanhoMaximo() {
        StringBuilder nomeLongo = new StringBuilder();
        for (int i = 0; i < 250; i++) {
            nomeLongo.append("a");
        }
        
        ValidationResult result = validator.validate(field, nomeLongo.toString(), "cliente.nome");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoNomeExcedendoTamanhoMaximo() {
        StringBuilder nomeLongo = new StringBuilder();
        for (int i = 0; i < 251; i++) {
            nomeLongo.append("a");
        }
        
        ValidationResult result = validator.validate(field, nomeLongo.toString(), "cliente.nome");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'nome' não deve ter mais que 250 caracteres"));
        assertTrue(result.getMessage().contains("Valor recebido tem 251 caracteres"));
        assertEquals("cliente.nome", result.getFieldPath());
        assertEquals(5, result.getOrdem());
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String nome;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("nome");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "", "cliente.nome");
        
        assertTrue(result.isValid());
    }
}
