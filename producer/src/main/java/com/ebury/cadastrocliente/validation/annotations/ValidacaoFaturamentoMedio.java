package com.ebury.cadastrocliente.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoFaturamentoMedio {
    int ordem() default 30;
    int valorMinimo() default 0;
    int valorMaximo() default Integer.MAX_VALUE;
} 