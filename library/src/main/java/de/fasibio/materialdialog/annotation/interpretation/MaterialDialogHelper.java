package de.fasibio.materialdialog.annotation.interpretation;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;

import de.fasibio.materialdialog.annotation.annotation.OnMaterialDialogClick;

/**
 * Created by simofa on 21.06.2016.
 */
public class MaterialDialogHelper {
    private Context con = null;

    public static class DefaultValue{
        public static String title = "MaterialDialog",message="This is a simple MaterialDialog.", positiveText="OK", neutralText = "NOT NOW", negativeText = "CANCEL";
    }

    private MaterialDialogHelper(){}

    public static void bind(Object classToBind, View view){
        new MaterialDialogHelper().ini(classToBind,view);
    }

    public static void bind(Context classToBind, Activity activity){
        new MaterialDialogHelper().ini(classToBind,activity);

    }

    private void ini(Object classToBind, View view){
        con = view.getContext();
        Method[] methods = classToBind.getClass().getDeclaredMethods();
        for (Method one : methods){
            View element = null;
            OnMaterialDialogClick clickannotation = one.getAnnotation(OnMaterialDialogClick.class);
            if (clickannotation != null){
                MaterialDialog.Builder dialogbu = null;
                dialogbu.title(getStringResOrDefault(clickannotation.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(clickannotation.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(clickannotation.positivColor());
                dialogbu.negativeColor(clickannotation.negativColor());
                dialogbu.neutralColor(clickannotation.neutralColor());
                element = view.findViewById(clickannotation.clickRes());
                AnnotaionHolder holder = new AnnotaionHolder(one,clickannotation.clickType(),dialogbu);
                element.setOnClickListener(new MaterialDialogHelperClickListener(holder,classToBind));

            }
        }

    }
    private void ini(Object classToBind, Activity view){
        con = view;
        Method[] methods = classToBind.getClass().getDeclaredMethods();
        for (Method one : methods){
            View element = null;
            OnMaterialDialogClick clickannotation = one.getAnnotation(OnMaterialDialogClick.class);
            if (clickannotation != null){
                MaterialDialog.Builder dialogbu = new MaterialDialog.Builder(con);
                dialogbu.title(getStringResOrDefault(clickannotation.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(clickannotation.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(clickannotation.positivColor());
                dialogbu.negativeColor(clickannotation.negativColor());
                dialogbu.neutralColor(clickannotation.neutralColor());
                element = view.findViewById(clickannotation.clickRes());
                AnnotaionHolder holder = new AnnotaionHolder(one,clickannotation.clickType(),dialogbu);
                element.setOnClickListener(new MaterialDialogHelperClickListener(holder,classToBind));

            }
        }
    }

    private  String getStringResOrDefault( int setting,String defaultv){
        if (setting != -1){
            return con.getResources().getString(setting);
        }
        return defaultv;
    }
}
