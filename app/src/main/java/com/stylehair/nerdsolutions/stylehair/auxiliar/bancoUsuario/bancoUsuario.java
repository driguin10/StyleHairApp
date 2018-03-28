package com.stylehair.nerdsolutions.stylehair.auxiliar.bancoUsuario;

/**
 * Created by Rodrigo on 21/03/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bancoUsuario extends SQLiteOpenHelper {


    public static final String NOME_BANCO = "bancoSamartSalao.db";
    public static final String TABELA = "notificacoes";
    public static final String ID = "_id";
    public  static final String TITULO = "titulo";
    public  static final String TEXTO = "texto";
    public  static final String VISUALIZACAO = "no";
    public  static final int VERSAO = 1;



    public bancoUsuario(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+"(" + ID + " integer primary key autoincrement," + TITULO + " text,"
                + TEXTO + " text," + VISUALIZACAO + " text)";
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