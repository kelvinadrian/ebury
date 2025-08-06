package com.ebury.cadastrocliente.validation.annotations.clientepj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo RamoDeAtividade do ClientePjDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClientePjRamoDeAtividade {
    int ordem() default 1;
}
