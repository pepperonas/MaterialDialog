package de.fasibio.materialdialog.annotation.interpretation;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import de.fasibio.materialdialog.annotation.annotation.OnMaterialDialogDismissListener;
import de.fasibio.materialdialog.annotation.annotation.OnMaterialDialogClick;
import de.fasibio.materialdialog.annotation.annotation.OnMaterialDialogShowListener;

/**
 * Created by simofa on 21.06.2016.
 */
public class MaterialDialogHelper {
    private Context con = null;
    private HashMap<Integer,AnnotaionHolder>  annotationsCollector = new HashMap<Integer, AnnotaionHolder>();
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
        ini(classToBind,(Activity)view.getContext());
    }

    private AnnotaionHolder getHolderFromHashMap(int res){
        if (annotationsCollector.get(res) == null){
            annotationsCollector.put(res,new AnnotaionHolder());
        }
        return annotationsCollector.get(res);
    }

    private void ini(Object classToBind, Activity view){


        con = view;
        Method[] methods = classToBind.getClass().getDeclaredMethods();
        for (Method one : methods){
            //View element = null;
            OnMaterialDialogClick clickannotation = one.getAnnotation(OnMaterialDialogClick.class);
            OnMaterialDialogDismissListener dismissannotation = one.getAnnotation(OnMaterialDialogDismissListener.class);
            OnMaterialDialogShowListener showannotation = one.getAnnotation(OnMaterialDialogShowListener.class);

            if (clickannotation != null){
                MaterialDialog.Builder dialogbu = new MaterialDialog.Builder(con);
                dialogbu.title(getStringResOrDefault(clickannotation.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(clickannotation.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(clickannotation.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(clickannotation.positivColor());
                dialogbu.negativeColor(clickannotation.negativColor());
                dialogbu.neutralColor(clickannotation.neutralColor());
                //element = view.findViewById(clickannotation.clickRes());
                AnnotaionHolder holder = getHolderFromHashMap(clickannotation.clickRes());
                holder.setMaterialDialogClickMethod(one);
                holder.setType(clickannotation.clickType());
                holder.setDialogbuilder(dialogbu);
                //element.setOnClickListener(new MaterialDialogHelperClickListener(holder,classToBind));
            }
            if (dismissannotation != null){
                getHolderFromHashMap(dismissannotation.clickRes()).setDismissListenerMethode(one);
            }
            if (showannotation != null){
                getHolderFromHashMap(showannotation.clickRes()).setShowListenerMethode(one);
            }

        }

        for(Map.Entry<Integer, AnnotaionHolder> entry : annotationsCollector.entrySet()) {
            View element  = view.findViewById(entry.getKey());
            element.setOnClickListener(new MaterialDialogHelperClickListener(entry.getValue(),classToBind));
        }
    }

    private  String getStringResOrDefault( int setting,String defaultv){
        if (setting != -1){
            return con.getResources().getString(setting);
        }
        return defaultv;
    }
}
