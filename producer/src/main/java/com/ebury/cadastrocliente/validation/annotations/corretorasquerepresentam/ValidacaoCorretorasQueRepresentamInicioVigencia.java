package com.ebury.cadastrocliente.validation.annotations.corretorasquerepresentam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo CorretorasQueRepresentamInicioVigencia do CorretoraQueRepresentaDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoCorretorasQueRepresentamInicioVigencia {
    int ordem() default 1;
}
