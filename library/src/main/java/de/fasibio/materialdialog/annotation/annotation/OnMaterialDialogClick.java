package de.fasibio.materialdialog.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.fasibio.materialdialog.annotation.interpretation.MaterialDialogClickTyp;

/**
 * Created by fasibio on 21.06.2016.
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

    /**
     * The String ressource for messages
     */
    int messageRes()default -1;

    /**
     * The String ressource for positive Text e.g. "YES"
     */
    int positiveTextRes()default -1;

    /**
     * The String ressource for negativ Text e.g. "No"
     */
    int negativeTextRes() default -1;

    /**
     * The String ressource for neutral Text e.g. "Not now"
     */
    int neutralTextRes() default -1;
    /**
     * The Color ressource for positive Text
     */
    int positivColor() default android.R.color.black;
    /**
     * The Color ressource for neutral Text
     */
    int neutralColor() default android.R.color.black;
    /**
     * The Color ressource for negativ Text
     */
    int negativColor() default android.R.color.black;

}
