package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Rodrigo on 19/02/2018.
 */

public class VerificaConexao {

    public  boolean verifica(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
