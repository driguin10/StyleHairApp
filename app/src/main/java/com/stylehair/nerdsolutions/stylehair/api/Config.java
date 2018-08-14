package com.stylehair.nerdsolutions.stylehair.api;

import android.os.StrictMode;

/**
 * Created by Rodrigo on 05/02/2018.
 */

public class Config {

    private String WebService = "http://stylehair.xyz/";
    private String chave = "R20p1g8S7m85ar92t";
    private String versao ="v1";
    private static String nomeBanco ="bancoSamartSalao.db";

    public String getWebService() {
        return WebService;
    }

    public String getChave() {
        return chave;
    }

    public String getVersao() {
        return versao;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

}
