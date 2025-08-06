package com.ebury.cadastrocliente.validation.annotations.cliente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo GerenteAnalistaOriginador do ClienteDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClienteGerenteAnalistaOriginador {
    int ordem() default 1;
}
