package com.ebury.cadastrocliente.validation.validators.cliente;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.cliente.ValidacaoClienteTipoDeManutencao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTipoDeManutencaoValidatorTest {

    private ClienteTipoDeManutencaoValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClienteTipoDeManutencaoValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClienteTipoDeManutencao(ordem = 1)
            private String cliTipoDeManutencao;
        }
        
        field = TestClass.class.getDeclaredField("cliTipoDeManutencao");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.cliTipoDeManutencao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'cliTipoDeManutencao' deve ser informado", result.getMessage());
        assertEquals("cliente.cliTipoDeManutencao", result.getFieldPath());
        assertEquals(1, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.cliTipoDeManutencao");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'cliTipoDeManutencao' deve ser informado", result.getMessage());
        assertEquals("cliente.cliTipoDeManutencao", result.getFieldPath());
        assertEquals(1, result.getOrdem());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, "X", "cliente.cliTipoDeManutencao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'cliTipoDeManutencao' deve ser I (Inclusão), A (Alteração) ou E (Exclusão)"));
        assertTrue(result.getMessage().contains("Valor recebido: 'X'"));
        assertEquals("cliente.cliTipoDeManutencao", result.getFieldPath());
        assertEquals(1, result.getOrdem());
    }

    @Test
    void testValidacaoValorInclusao() {
        ValidationResult result = validator.validate(field, "I", "cliente.cliTipoDeManutencao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorAlteracao() {
        ValidationResult result = validator.validate(field, "A", "cliente.cliTipoDeManutencao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorExclusao() {
        ValidationResult result = validator.validate(field, "E", "cliente.cliTipoDeManutencao");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCaseInsensitive() {
        // Testa que a validação é case-sensitive
        ValidationResult result = validator.validate(field, "i", "cliente.cliTipoDeManutencao");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Valor recebido: 'i'"));
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String cliTipoDeManutencao;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("cliTipoDeManutencao");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.cliTipoDeManutencao");
        
        assertTrue(result.isValid());
    }
}
