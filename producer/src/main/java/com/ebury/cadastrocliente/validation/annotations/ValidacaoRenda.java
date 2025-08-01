package com.ebury.cadastrocliente.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar campos de renda.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoRenda {
    int ordem() default 5;
    double valorMinimo() default 100.0;
    double valorMaximo() default 1000000000.0;
} 