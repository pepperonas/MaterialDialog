package de.fasibio.materialdialog.annotation.interpretation;

import android.content.Context;
import android.view.View;

import com.pepperonas.materialdialog.MaterialDialog;

import java.lang.reflect.Method;

import de.fasibio.materialdialog.annotation.annotation.OnDialogNegativClick;
import de.fasibio.materialdialog.annotation.annotation.OnDialogNeutralClick;
import de.fasibio.materialdialog.annotation.annotation.OnDialogPositiveClick;

/**
 * Created by simofa on 20.06.2016.
 */

public class MaterialDialogHelper {

    private static Context con;
    private static String getStringResOrDefault( int setting,String defaultv){
        if (setting != -1){
         return con.getResources().getString(setting);
        }
        return defaultv;
    }



    public static class DefaultValue{
        public static String title = "MaterialDialog",message="This is a simple MaterialDialog.", positiveText="OK", neutralText = "NOT NOW", negativeText = "CANCEL";
    }

    public MaterialDialogHelper(){

    }

    public static void bind(Object classToBind, View view){
        Context con = view.getContext();
        Method[] methods = classToBind.getClass().getDeclaredMethods();
        for (Method one : methods){
            OnDialogNegativClick negativClick = one.getAnnotation(OnDialogNegativClick.class);
            OnDialogNeutralClick neutralClick = one.getAnnotation(OnDialogNeutralClick.class);
            OnDialogPositiveClick positiveClick = one.getAnnotation(OnDialogPositiveClick.class);
            MaterialDialog.Builder dialogbu = null;
            if (negativClick != null || neutralClick != null || positiveClick != null){
                dialogbu = new MaterialDialog.Builder(view.getContext());
            }
            if (negativClick != null){
                //inizialize Click
                dialogbu.title(getStringResOrDefault(negativClick.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(negativClick.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(negativClick.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(negativClick.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(negativClick.positivColor());
                dialogbu.negativeColor(negativClick.negativColor());
                dialogbu.neutralColor(negativClick.neutralColor());
            }
            if (neutralClick != null){
                //inizialize Click
                dialogbu.title(getStringResOrDefault(neutralClick.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(neutralClick.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(neutralClick.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(neutralClick.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(neutralClick.positivColor());
                dialogbu.negativeColor(neutralClick.negativColor());
                dialogbu.neutralColor(neutralClick.neutralColor());
            }
            if (positiveClick != null){
                //inizialize Click
                dialogbu.title(getStringResOrDefault(positiveClick.titleRes(),DefaultValue.title));
                dialogbu.positiveText(getStringResOrDefault(positiveClick.positiveTextRes(),DefaultValue.positiveText));
                dialogbu.neutralText(getStringResOrDefault(positiveClick.negativTextRes(),DefaultValue.neutralText));
                dialogbu.negativeText(getStringResOrDefault(positiveClick.negativTextRes(),DefaultValue.negativeText));
                dialogbu.positiveColor(positiveClick.positivColor());
                dialogbu.negativeColor(positiveClick.negativColor());
                dialogbu.neutralColor(positiveClick.neutralColor());
            }

        }



    }


}
