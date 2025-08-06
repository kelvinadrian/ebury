package com.ebury.cadastrocliente.validation.annotations.clientepf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo MunicipioDaNaturalidade do ClientePfDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoClientePfMunicipioDaNaturalidade {
    int ordem() default 1;
}
