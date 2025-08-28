package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteDataDoCadastro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDataDoCadastroValidatorTest {

    private ClienteDataDoCadastroValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteDataDoCadastroValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteDataDoCadastro(ordem = 6)
            private String dataDoCadastro;
        }
        
        field = TestClass.class.getDeclaredField("dataDoCadastro");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.dataDoCadastro");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.dataDoCadastro");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoDataValida() {
        ValidationResult result = validator.validate(field, "01012024", "cliente.dataDoCadastro");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoDataInvalida() {
        ValidationResult result = validator.validate(field, "32132024", "cliente.dataDoCadastro");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do cadastro inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '32132024'"));
        assertEquals("cliente.dataDoCadastro", result.getFieldPath());
        assertEquals(6, result.getOrdem());
    }

    @Test
    void testValidacaoDataComFormatoInvalido() {
        ValidationResult result = validator.validate(field, "01/01/2024", "cliente.dataDoCadastro");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do cadastro inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '01/01/2024'"));
        assertEquals("cliente.dataDoCadastro", result.getFieldPath());
        assertEquals(6, result.getOrdem());
    }

    @Test
    void testValidacaoDataComTamanhoInvalido() {
        ValidationResult result = validator.validate(field, "010124", "cliente.dataDoCadastro");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do cadastro inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '010124'"));
        assertEquals("cliente.dataDoCadastro", result.getFieldPath());
        assertEquals(6, result.getOrdem());
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String dataDoCadastro;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("dataDoCadastro");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.dataDoCadastro");
        
        assertTrue(result.isValid());
    }
}
