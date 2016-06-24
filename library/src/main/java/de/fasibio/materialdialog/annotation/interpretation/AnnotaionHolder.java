package de.fasibio.materialdialog.annotation.interpretation;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;

/**
 *
 * A Helperclass for Annotation.
 * It hold all Method for one Res Dialog
 * Created by fasibio on 21.06.2016.
 */
public class AnnotaionHolder {

    private MaterialDialog.Builder dialogbuilder;
    private Method materialDialogClickMethode = null,showListenerMethode = null, dismissListenerMethode = null;
    private MaterialDialogClickTyp type;

    protected AnnotaionHolder(){}
    protected AnnotaionHolder(Method materialDialogClickMethode, MaterialDialogClickTyp type, MaterialDialog.Builder dialogbuilder) {
        setDialogbuilder(dialogbuilder);
        setMaterialDialogClickMethod(materialDialogClickMethode);
        setType(type);
    }


    /**
     *
     * @return the kind of clicktype
     */
    public MaterialDialogClickTyp getType() {
        return type;
    }

    /**
     * Set the typ of this holder
     */
    public void setType(MaterialDialogClickTyp type) {
        this.type = type;
    }
    public MaterialDialog.Builder getDialogbuilder() {
        return dialogbuilder;
    }

    /**
     *
     * Set the MatrialDialog Builder
     */
    public void setDialogbuilder(MaterialDialog.Builder dialogbuilder) {
        this.dialogbuilder = dialogbuilder;
    }


    /**
     *
     * @return the annotationmethode for the click event
     */
    public Method getMaterialDialogClickMethode() {
        return materialDialogClickMethode;
    }

    /**
     *
     * Set the clickmethode for the annotation
     */
    public void setMaterialDialogClickMethod(Method materialDialogClickmethod) {
        this.materialDialogClickMethode = materialDialogClickmethod;
    }

    /**
     *
     * @return the annotationmethode for the showdialog event
     */
    public Method getShowListenerMethode() {
        return showListenerMethode;
    }

    public void setShowListenerMethode(Method showListenerMethode) {
        this.showListenerMethode = showListenerMethode;
    }

    /**
     *
     * @return the annotationmethode for the dismissmethode
     */
    public Method getDismissListenerMethode() {
        return dismissListenerMethode;
    }

    public void setDismissListenerMethode(Method dismissListenerMethode) {
        this.dismissListenerMethode = dismissListenerMethode;
    }

}
