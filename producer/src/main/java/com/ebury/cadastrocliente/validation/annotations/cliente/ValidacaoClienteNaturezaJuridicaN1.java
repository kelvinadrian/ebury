package com.ebury.cadastrocliente.validation.annotations.cliente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo NaturezaJuridicaN1 do ClienteDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClienteNaturezaJuridicaN1 {
    int ordem() default 1;
}
