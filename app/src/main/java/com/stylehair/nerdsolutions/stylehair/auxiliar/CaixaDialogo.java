package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Rodrigo on 19/02/2018.
 */

public class CaixaDialogo {

    Activity activity;
    ProgressDialog dialog;
    String Texto;

    public void MenssagemDialog(Activity activity, String texto)
    {
        this.activity = activity;
        this.Texto = texto;
        criaDialog();
    }

    public void criaDialog()
    {
        dialog = new ProgressDialog(this.activity);
        dialog.setMessage(this.Texto);
        dialog.setIndeterminate(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.show();
    }

    public  void fecharCaixa()
    {
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
    }
}
