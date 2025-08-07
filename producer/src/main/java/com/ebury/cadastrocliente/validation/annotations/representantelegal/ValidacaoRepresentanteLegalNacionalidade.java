package com.ebury.cadastrocliente.validation.annotations.representantelegal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo Nacionalidade do RepresentanteLegalDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoRepresentanteLegalNacionalidade {
    int ordem() default 1;
} 