package com.ebury.cadastrocliente.validation.annotations.contacorrente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo Desativada do ContaCorrenteDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoContaCorrenteDesativada {
    int ordem() default 1;
}
