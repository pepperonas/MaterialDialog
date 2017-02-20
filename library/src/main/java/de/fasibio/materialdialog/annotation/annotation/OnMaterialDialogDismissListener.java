package de.fasibio.materialdialog.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You can only use, if you use the annotation @OnMaterialDialogClick on a other methode in the same class
 * Created by fasibio on 22.06.2016.
 */
@Retention(value= RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface OnMaterialDialogDismissListener {
    /**
     * the resource this Listener is used for
     * it have to be the same like the @OnMaterialDialogClick anntotation
     */
    int clickRes();
}
