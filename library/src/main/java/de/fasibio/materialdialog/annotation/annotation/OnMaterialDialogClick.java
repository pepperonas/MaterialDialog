package de.fasibio.materialdialog.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.fasibio.materialdialog.annotation.interpretation.MaterialDialogClickTyp;

/**
 * Created by simofa on 21.06.2016.
 */
@Retention(value= RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface OnMaterialDialogClick {
    /**
     * the resource this Listener is used for
     */
    int clickRes();
    MaterialDialogClickTyp clickType() default MaterialDialogClickTyp.positive;
    /**
     * The path to the title
     */
    int titleRes() default -1;
    int messageRes()default -1;
    int positiveTextRes()default -1;
    int negativTextRes() default -1;
    int positivColor() default android.R.color.black;
    int neutralColor() default android.R.color.black;
    int negativColor() default android.R.color.black;

}
