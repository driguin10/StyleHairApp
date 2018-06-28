package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Tutorial {
    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor editorShared ;

    public Tutorial(Context context) {
        this.getSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editorShared = getSharedPreferences.edit();
    }

    public void salvaShared(String codigoTutorial){
        editorShared.putBoolean(codigoTutorial,true);
        editorShared.apply();
        editorShared.commit();
    }

    public boolean verTutorial(String codigoTutorial)
    {
        return getSharedPreferences.getBoolean(codigoTutorial,false);
    }

}
