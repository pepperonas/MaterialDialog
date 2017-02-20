package de.fasibio.materialdialog.annotation.interpretation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 *
 * Created by fasibio on 21.06.2016.
 * A Helperclass
 * It use the holder to trigger the events
 */
public class MaterialDialogHelperClickListener implements View.OnClickListener{

    private AnnotaionHolder holder;
    private Object classToBind;
    protected MaterialDialogHelperClickListener(AnnotaionHolder holder,Object classToBind){
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
        if (holder.getShowListenerMethode() != null){
            holder.getDialogbuilder().showListener(new MaterialDialog.ShowListener() {
                @Override
                public void onShow(AlertDialog dialog) {
                    triggerShowEvent(dialog);
                }
            });
        }
        if (holder.getDismissListenerMethode() != null){
            holder.getDialogbuilder().dismissListener(new MaterialDialog.DismissListener() {
                @Override
                public void onDismiss() {
                    triggerDismissEvent();
                }
            });
        }
        holder.getDialogbuilder().show();
    }

    private void triggerShowEvent(AlertDialog dialog){
        Method m = holder.getShowListenerMethode();
        m.setAccessible(true);
        Type[] parameter = m.getParameterTypes();
        if (parameter.length > 1){
            throw new RuntimeException("Binding methode have to much parameters max allow 1 Type:AlertDialog ");
        }
        try{
            switch (parameter.length){
                case 0: m.invoke(classToBind);
                    break;
                case 1: m.invoke(classToBind,dialog);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void triggerDismissEvent(){
        Method m = holder.getDismissListenerMethode();
        m.setAccessible(true);
        try{
            m.invoke(classToBind);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void triggerClickEvent(MaterialDialog dialog,MaterialDialogClickTyp type){
        int dialogparamvalue = -1;
        int dialogclickparamvalue = -1;
        Method m = holder.getMaterialDialogClickMethode();
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
