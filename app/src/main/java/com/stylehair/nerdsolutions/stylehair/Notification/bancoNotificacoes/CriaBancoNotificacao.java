package com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by allanromanato on 5/27/15.
 */
public class CriaBancoNotificacao extends SQLiteOpenHelper {


    public static final String NOME_BANCO = "bancoStyleHair.db";
    public static final String TABELA = "notificacoes";
    public static final String ID = "_id";
    public static final String TITULO = "titulo";
    public static final String TEXTO = "texto";
    public static final String HORA = "hora";
    public static final String VISUALIZACAO = "visualizacao";
    public static final String NOME_SALAO = "nome_salao";
    public static final String ID_USUARIO ="id_usuario";
    public static final int VERSAO = 1;



    public CriaBancoNotificacao(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"(" + ID + " integer primary key autoincrement," + TITULO + " text,"
                + TEXTO + " text," + HORA + " text," + NOME_SALAO +" text," + ID_USUARIO+" text,"  + VISUALIZACAO + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS";
        db.execSQL(drop + TABELA);
        onCreate(db);
    }



    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}