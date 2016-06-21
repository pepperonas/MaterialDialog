package de.fasibio.materialdialog.annotation.interpretation;

import android.app.Dialog;
import android.view.View;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by simofa on 21.06.2016.
 */
public class MaterialDialogHelperClickListener implements View.OnClickListener{

    private AnnotaionHolder holder;
    private Object classToBind;
    public MaterialDialogHelperClickListener(AnnotaionHolder holder,Object classToBind){
        this.holder = holder;
        this.classToBind = classToBind;
    }

    @Override
    public void onClick(View v) {
        holder.getDialogbuilder().buttonCallback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onAny(MaterialDialog dialog) {
                //You can not use in annotation

//               if (holder.getType() == MaterialDialogClickTyp.all){
//                    triggerClickEvent(dialog,MaterialDialogClickTyp.all);
//               }
            }

            @Override
            public void onPositive(MaterialDialog dialog) {
                if (holder.getType() == MaterialDialogClickTyp.positive || holder.getType() == MaterialDialogClickTyp.all){
                    triggerClickEvent(dialog,MaterialDialogClickTyp.positive);
                }
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                if (holder.getType() == MaterialDialogClickTyp.neutral || holder.getType() == MaterialDialogClickTyp.all){
                    triggerClickEvent(dialog,MaterialDialogClickTyp.neutral);
                }
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                if (holder.getType() == MaterialDialogClickTyp.negative || holder.getType() == MaterialDialogClickTyp.all){
                    triggerClickEvent(dialog,MaterialDialogClickTyp.negative);
                }
            }
        });
        holder.getDialogbuilder().show();
    }

    private void triggerClickEvent(MaterialDialog dialog,MaterialDialogClickTyp type){
        int dialogparamvalue = -1;
        int dialogclickparamvalue = -1;
        Method m = holder.getMethod();
        m.setAccessible(true);
        Type[] parameter =  m.getParameterTypes();
        if (parameter.length > 2){
            throw new RuntimeException("Binding methode have to much parameters max allow 2 Type: MaterialDialog,MaterialDialogClickTyp ");
        }
        for (int i = 0; i < parameter.length; i++) {
            if (parameter[i] instanceof MaterialDialog) {
                dialogparamvalue = i;
            }
            if (((Class<?>)parameter[i]).isEnum() ) {
                dialogclickparamvalue = i;
            }
        }
        try {
            switch (parameter.length) {
                case 0 :m.invoke(classToBind);
                    break;
                case 1 :{
                    if (dialogclickparamvalue != -1){
                        m.invoke(classToBind,type);
                    }else{
                        m.invoke(classToBind,dialog);
                    }
                }
                break;
                case 2 :{
                    if (dialogclickparamvalue == 0){
                        m.invoke(classToBind,type,dialog);
                    }else{
                        m.invoke(classToBind,dialog,type);
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}