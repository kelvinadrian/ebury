package com.ebury.cadastrocliente.validation.annotations.endereco;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo TipoDeLogradouro do EnderecoDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoEnderecoTipoDeLogradouro {
    int ordem() default 1;
}
