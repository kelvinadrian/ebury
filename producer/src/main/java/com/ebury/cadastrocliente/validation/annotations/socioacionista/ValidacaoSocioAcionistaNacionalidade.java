package com.ebury.cadastrocliente.validation.annotations.socioacionista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo Nacionalidade do SocioAcionistaDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoSocioAcionistaNacionalidade {
    int ordem() default 1;
}
