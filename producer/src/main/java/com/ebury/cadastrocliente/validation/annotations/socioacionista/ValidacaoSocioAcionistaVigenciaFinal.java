package com.ebury.cadastrocliente.validation.annotations.socioacionista;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo VigenciaFinal do SocioAcionistaDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoSocioAcionistaVigenciaFinal {
    int ordem() default 1;
}
