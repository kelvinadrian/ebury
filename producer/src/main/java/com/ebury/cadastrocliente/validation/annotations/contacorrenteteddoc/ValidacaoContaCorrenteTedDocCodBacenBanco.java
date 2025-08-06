package com.ebury.cadastrocliente.validation.annotations.contacorrenteteddoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo CodBacenBanco do ContaCorrenteTedDocDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoContaCorrenteTedDocCodBacenBanco {
    int ordem() default 1;
}
