package com.ebury.cadastrocliente.validation.validators.clientepf;

import com.ebury.cadastrocliente.validation.ValidationResult;
import com.ebury.cadastrocliente.validation.annotations.clientepf.ValidacaoClientePfSexo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ClientePfSexoValidatorTest {

    private ClientePfSexoValidator validator;
    private Field field;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        validator = new ClientePfSexoValidator();
        
        // Criar um campo de teste com a anotação
        class TestClass {
            @ValidacaoClientePfSexo(ordem = 21)
            private String sexo;
        }
        
        field = TestClass.class.getDeclaredField("sexo");
    }

    @Test
    void testValidacaoCampoVazio() {
        ValidationResult result = validator.validate(field, "", "cliente.clientePf.sexo");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'sexo' deve ser informado para inclusão de cliente pessoa física", result.getMessage());
        assertEquals("cliente.clientePf.sexo", result.getFieldPath());
        assertEquals(21, result.getOrdem());
    }

    @Test
    void testValidacaoCampoNulo() {
        ValidationResult result = validator.validate(field, null, "cliente.clientePf.sexo");
        
        assertFalse(result.isValid());
        assertEquals("Campo 'sexo' deve ser informado para inclusão de cliente pessoa física", result.getMessage());
        assertEquals("cliente.clientePf.sexo", result.getFieldPath());
        assertEquals(21, result.getOrdem());
    }

    @Test
    void testValidacaoValorInvalido() {
        ValidationResult result = validator.validate(field, "INVALIDO", "cliente.clientePf.sexo");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Campo 'sexo' deve ser FEMININO ou MASCULINO"));
        assertTrue(result.getMessage().contains("Valor recebido: 'INVALIDO'"));
        assertEquals("cliente.clientePf.sexo", result.getFieldPath());
        assertEquals(21, result.getOrdem());
    }

    @Test
    void testValidacaoValorFeminino() {
        ValidationResult result = validator.validate(field, "FEMININO", "cliente.clientePf.sexo");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorMasculino() {
        ValidationResult result = validator.validate(field, "MASCULINO", "cliente.clientePf.sexo");
        
        assertTrue(result.isValid());
    }

    @Test
    void testValidacaoValorCaseInsensitive() {
        // Testa que a validação é case-sensitive
        ValidationResult result = validator.validate(field, "feminino", "cliente.clientePf.sexo");
        
        assertFalse(result.isValid());
        assertTrue(result.getMessage().contains("Valor recebido: 'feminino'"));
    }

    @Test
    void testValidacaoSemAnotacao() throws NoSuchFieldException {
        // Testa campo sem a anotação
        class TestClassWithoutAnnotation {
            private String sexo;
        }
        
        Field fieldWithoutAnnotation = TestClassWithoutAnnotation.class.getDeclaredField("sexo");
        ValidationResult result = validator.validate(fieldWithoutAnnotation, "INVALIDO", "cliente.clientePf.sexo");
        
        assertTrue(result.isValid());
    }
}
