package com.ebury.cadastrocliente.validation.annotations.contato;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo TipoDeManutencao do ContatoDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoContatoTipoDeManutencao {
    int ordem() default 1;
}
