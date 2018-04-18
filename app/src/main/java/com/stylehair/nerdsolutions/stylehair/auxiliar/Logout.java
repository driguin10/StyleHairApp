package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.telas.login.logar;

public class Logout {


    public void deslogar(Activity activity,boolean principal){
        SharedPreferences getSharedPreferencesL = PreferenceManager
                .getDefaultSharedPreferences(activity);

        String topic = getSharedPreferencesL.getString("topicoNotificacao","");
        SharedPreferences.Editor e = getSharedPreferencesL.edit();
        e.clear();
        e.apply();
        e.commit();
        e.putBoolean("firstStart",false);
        //e.putBoolean("logado", false);
       // e.putInt("idLogin",-1);
       // e.putString("typeUserApp","");
        //e.putString("nomeUser","");
       // e.putString("linkImagem","");
        e.apply();
        e.commit();
        Log.d("xex", "topico log= "+ topic);
        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
        if(!topic.equals("")) {
            topicoNotificacao.removeTopico(topic);
        }


        Intent intent = new Intent(activity,logar.class);
        if(!principal)
            ActivityCompat.finishAffinity(activity);
        activity.startActivity(intent);
        if(principal)
            activity.finish();
    }
}
