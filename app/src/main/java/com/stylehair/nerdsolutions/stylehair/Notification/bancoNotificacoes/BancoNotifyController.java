package com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoNotifyController {
    private SQLiteDatabase db;
    private CriaBancoNotificacao banco;

    public BancoNotifyController(Context context){
        banco = new CriaBancoNotificacao(context);
    }

    public String insereDado(String titulo, String texto, String hora, String visualizado, String nome_salao, String id_usuario){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBancoNotificacao.TITULO, titulo);
        valores.put(CriaBancoNotificacao.TEXTO, texto);
        valores.put(CriaBancoNotificacao.HORA, hora);
        valores.put(CriaBancoNotificacao.VISUALIZACAO, visualizado);
        valores.put(CriaBancoNotificacao.NOME_SALAO,nome_salao);
        valores.put(CriaBancoNotificacao.ID_USUARIO,id_usuario);

        resultado = db.insert(CriaBancoNotificacao.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "1";
        else
            return "0";

    }

    public Cursor carregaDados(String id){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.TITULO,banco.TEXTO,banco.HORA,banco.VISUALIZACAO,banco.NOME_SALAO,banco.ID_USUARIO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, "id_usuario = ?", new String[] { id }, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaQuantidade(String id){
        Cursor cursor;
        String[] campos =  {banco.ID,banco.TITULO,banco.TEXTO,banco.HORA,banco.VISUALIZACAO,banco.NOME_SALAO,banco.ID_USUARIO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, "id_usuario = ? AND visualizacao = ?", new String[] { id , "0"}, null, null, null, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public void alteraRegistro(int idnotificacao,String titulo, String texto, String hora, String visualizado, String nome_salao){
       /* ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = CriaBancoNotificacao.ID_USUARIO + "=" + id_usuario;
        valores = new ContentValues();
        valores.put(CriaBancoNotificacao.TITULO, titulo);
        valores.put(CriaBancoNotificacao.TEXTO, texto);
        valores.put(CriaBancoNotificacao.HORA, hora);
        valores.put(CriaBancoNotificacao.VISUALIZACAO, visualizado);
        valores.put(CriaBancoNotificacao.NOME_SALAO,nome_salao);
        valores.put(CriaBancoNotificacao.ID_USUARIO,id_usuario);
        db.update(CriaBancoNotificacao.TABELA,valores,where,null);
        db.close();*/
        db = banco.getWritableDatabase();
        db.execSQL("UPDATE notificacoes SET titulo ='"+titulo+"', texto ='"+texto+"', hora='"+hora+"', nome_salao='"+nome_salao+"', visualizacao='"+visualizado+"' WHERE _id ='"+idnotificacao+"'");
        db.close();
    }

    public void deletaRegistro(int id){
        db = banco.getReadableDatabase();
        db.execSQL("DELETE FROM notificacoes WHERE _id = " + id);
        db.close();
    }
}