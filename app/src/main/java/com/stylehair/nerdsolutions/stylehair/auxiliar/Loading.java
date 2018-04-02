package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;

public class Loading {
    private AlertDialog alerta;
    private View view;


     public Loading(Activity activity){
        LayoutInflater li = activity.getLayoutInflater();
        this.view = li.inflate(R.layout.activity_carregando, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(this.view);
        this.alerta = builder.create();
        this.alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.alerta.setCanceledOnTouchOutside(false);
    }

    public void abrir(String menssagem){
        TextView texto = (TextView) view.findViewById(R.id.menssagem_loading);
        texto.setText(menssagem);
        this.alerta.show();
    }

    public void fechar(){
        this.alerta.dismiss();
    }
}
