package com.ebury.cadastrocliente.validation.annotations.clientepj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo UfEmissorInscricaoEstadual do ClientePjDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClientePjUfEmissorInscricaoEstadual {
    int ordem() default 1;
}
