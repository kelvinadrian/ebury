package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfDataDocumIdentificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClientePfDataDocumIdentificacaoValidatorTest {

    private ClientePfDataDocumIdentificacaoValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClientePfDataDocumIdentificacaoValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClientePfDataDocumIdentificacao(ordem = 25)
            private String dataDocumIdentificacao;
        }
        
        field = TestClass.class.getDeclaredField("dataDocumIdentificacao");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.clientePf.dataDocumIdentificacao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'dataDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física", result.getMessage());
        assertEquals("cliente.clientePf.dataDocumIdentificacao", result.getFieldPath());
        assertEquals(25, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.clientePf.dataDocumIdentificacao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'dataDocumIdentificacao' deve ser informado para inclusão de cliente pessoa física", result.getMessage());
        assertEquals("cliente.clientePf.dataDocumIdentificacao", result.getFieldPath());
        assertEquals(25, result.getOrdem());
    }

    @Test
    void testValidacaoDataValida() {
        ValidationResult result = validator.validate(field, "01012024", "cliente.clientePf.dataDocumIdentificacao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoDataInvalida() {
        ValidationResult result = validator.validate(field, "32132024", "cliente.clientePf.dataDocumIdentificacao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do documento de identificação inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '32132024'"));
        assertEquals("cliente.clientePf.dataDocumIdentificacao", result.getFieldPath());
        assertEquals(25, result.getOrdem());
    }

    @Test
    void testValidacaoDataComFormatoInvalido() {
        ValidationResult result = validator.validate(field, "01/01/2024", "cliente.clientePf.dataDocumIdentificacao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do documento de identificação inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '01/01/2024'"));
        assertEquals("cliente.clientePf.dataDocumIdentificacao", result.getFieldPath());
        assertEquals(25, result.getOrdem());
    }

    @Test
    void testValidacaoDataComTamanhoInvalido() {
        ValidationResult result = validator.validate(field, "010124", "cliente.clientePf.dataDocumIdentificacao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Data do documento de identificação inválida"));
        assertTrue(result.getMessage().contains("Formato esperado: ddMMyyyy"));
        assertTrue(result.getMessage().contains("Valor recebido: '010124'"));
        assertEquals("cliente.clientePf.dataDocumIdentificacao", result.getFieldPath());
        assertEquals(25, result.getOrdem());
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String dataDocumIdentificacao;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("dataDocumIdentificacao");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.clientePf.dataDocumIdentificacao");
        
        assertTrue(result.isValid());
    }
}
