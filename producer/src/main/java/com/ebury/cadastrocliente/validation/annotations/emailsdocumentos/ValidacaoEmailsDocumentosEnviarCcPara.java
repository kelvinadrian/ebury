package com.ebury.cadastrocliente.validation.annotations.emailsdocumentos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para validar o campo sEnviarCcPara do EmailsDocumentoDTO.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacaoEmailsDocumentosEnviarCcPara {
    int ordem() default 1;
}
