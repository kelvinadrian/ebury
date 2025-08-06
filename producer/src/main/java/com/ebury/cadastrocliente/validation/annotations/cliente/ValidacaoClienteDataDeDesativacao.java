package com.ebury.cadastrocliente.validation.annotations.cliente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo DataDeDesativacao do ClienteDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClienteDataDeDesativacao {
    int ordem() default 1;
}
