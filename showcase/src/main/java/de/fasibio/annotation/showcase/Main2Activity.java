package de.fasibio.annotation.showcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pepperonas.materialdialog.MaterialDialog;
import com.pepperonas.showcase.R;

import de.fasibio.materialdialog.annotation.annotation.OnMaterialDialogClick;
import de.fasibio.materialdialog.annotation.interpretation.MaterialDialogClickTyp;
import de.fasibio.materialdialog.annotation.interpretation.MaterialDialogHelper;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MaterialDialogHelper.bind(this,this);
        MaterialDialogHelper.DefaultValue.message="My spezial Message";
    }

    @OnMaterialDialogClick(clickRes = R.id.ClickYesNoParam,clickType = MaterialDialogClickTyp.positive, messageRes = R.string.app_name)
    public void onYesClick(){
        Toast.makeText(this,"Yes was Clicked",Toast.LENGTH_SHORT).show();

    }


    @OnMaterialDialogClick(clickRes = R.id.ClickYesOnePara,clickType = MaterialDialogClickTyp.positive)
    public void onYesClick(MaterialDialog materialDialog){
        Toast.makeText(this,"Yes was Clicked",Toast.LENGTH_SHORT).show();

    }


    @OnMaterialDialogClick(clickRes = R.id.ClickYesTWOPara,clickType = MaterialDialogClickTyp.positive)
    public void onYesClick(MaterialDialog materialDialog,MaterialDialogClickTyp type){
        Toast.makeText(this,"Yes was Clicked "+ type,Toast.LENGTH_SHORT).show();

    }


    @OnMaterialDialogClick(clickRes = R.id.ClickAllTwoPara,clickType = MaterialDialogClickTyp.all)
    public void onAllClick(MaterialDialog materialDialog,MaterialDialogClickTyp type){
        Toast.makeText(this,"Yes was Clicked "+type,Toast.LENGTH_SHORT).show();

    }


}
