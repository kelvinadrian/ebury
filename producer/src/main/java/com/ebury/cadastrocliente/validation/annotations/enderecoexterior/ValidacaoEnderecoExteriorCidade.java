package com.ebury.cadastrocliente.validation.annotations.enderecoexterior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo EnderecoExteriorCidade do EnderecoNoExteriorDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoEnderecoExteriorCidade {
    int ordem() default 1;
}
