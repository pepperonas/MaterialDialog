package de.fasibio.materialdialog.annotation.interpretation;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;

/**
 * Created by simofa on 21.06.2016.
 */
public class AnnotaionHolder {

    private MaterialDialog.Builder dialogbuilder;
    private Method method;
    private MaterialDialogClickTyp type;
    public AnnotaionHolder(Method method, MaterialDialogClickTyp type, MaterialDialog.Builder dialogbuilder) {
        setDialogbuilder(dialogbuilder);
        setMethod(method);
        setType(type);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
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

}
