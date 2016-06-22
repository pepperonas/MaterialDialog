package de.fasibio.materialdialog.annotation.interpretation;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;

/**
 * Created by simofa on 21.06.2016.
 */
public class AnnotaionHolder {

    private MaterialDialog.Builder dialogbuilder;
    private Method materialDialogClickMethode = null,showListenerMethode = null, dismissListenerMethode = null;
    private MaterialDialogClickTyp type;

    public AnnotaionHolder(){}
    public AnnotaionHolder(Method materialDialogClickMethode, MaterialDialogClickTyp type, MaterialDialog.Builder dialogbuilder) {
        setDialogbuilder(dialogbuilder);
        setMaterialDialogClickMethod(materialDialogClickMethode);
        setType(type);
    }


    public MaterialDialogClickTyp getType() {
        return type;
    }

    public void setType(MaterialDialogClickTyp type) {
        this.type = type;
    }
    public MaterialDialog.Builder getDialogbuilder() {
        return dialogbuilder;
    }

    public void setDialogbuilder(MaterialDialog.Builder dialogbuilder) {
        this.dialogbuilder = dialogbuilder;
    }


    public Method getMaterialDialogClickMethode() {
        return materialDialogClickMethode;
    }

    public void setMaterialDialogClickMethod(Method materialDialogClickmethod) {
        this.materialDialogClickMethode = materialDialogClickmethod;
    }

    public Method getShowListenerMethode() {
        return showListenerMethode;
    }

    public void setShowListenerMethode(Method showListenerMethode) {
        this.showListenerMethode = showListenerMethode;
    }

    public Method getDismissListenerMethode() {
        return dismissListenerMethode;
    }

    public void setDismissListenerMethode(Method dismissListenerMethode) {
        this.dismissListenerMethode = dismissListenerMethode;
    }

}
