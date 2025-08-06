package com.ebury.cadastrocliente.validation.annotations.operacoespermitidas;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo OperacoesPermitidasTipo do OperacaoPermitidaDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoOperacoesPermitidasTipoOperacaoPermitida {
    int ordem() default 1;
}
