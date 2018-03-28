package com.stylehair.nerdsolutions.stylehair.auxiliar.bancoUsuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UsuarioController {

    private SQLiteDatabase db;
    private bancoUsuario banco;

    public UsuarioController(Context context){
        banco = new bancoUsuario(context);
    }

    public String insereDado(String titulo, String texto, String visualizado){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(bancoUsuario.TITULO, titulo);
        valores.put(bancoUsuario.TEXTO, texto);
        valores.put(bancoUsuario.VISUALIZACAO, visualizado);

        resultado = db.insert(bancoUsuario.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.TITULO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public void alteraRegistro(int id, String titulo, String texto, String visualizado){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();

        where = bancoUsuario.ID + "=" + id;

        valores = new ContentValues();
        valores.put(bancoUsuario.TITULO, titulo);
        valores.put(bancoUsuario.TEXTO, texto);
        valores.put(bancoUsuario.VISUALIZACAO, visualizado);

        db.update(bancoUsuario.TABELA,valores,where,null);
        db.close();
    }

    public void deletaRegistro(int id){
        String where = bancoUsuario.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(bancoUsuario.TABELA,where,null);
        db.close();
    }
}