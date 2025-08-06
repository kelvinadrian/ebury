package com.ebury.cadastrocliente.validation.annotations.documentos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo DocumentosIdsDosArquivos do ListaDeDocumentosDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoDocumentosIdsDosArquivos {
    int ordem() default 1;
}
