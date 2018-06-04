package com.stylehair.nerdsolutions.stylehair.Notification;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.Adaptador_notify;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.CriaBancoNotificacao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.ArrayList;
import java.util.List;

public class notificacao extends AppCompatActivity  {
    RecyclerView lista;
    int idLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_notificacoes);
        setSupportActionBar(myToolbar);

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        idLogin = getSharedPreferences.getInt("idLogin", -1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("NOTIFICAÇÕES");
        Drawable upArrow = ContextCompat.getDrawable(notificacao.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(notificacao.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        lista = (RecyclerView) findViewById(R.id.listNotify);
        lista.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);

        BancoNotifyController crud = new BancoNotifyController(getBaseContext());
        Cursor cursor = crud.carregaDados(String.valueOf(idLogin));
        final List<menssagem> conteudo_menssagem = new ArrayList<>();




        if (cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.ID));
                String titulo = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.TITULO));
                String texto = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.TEXTO));
                String hora = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.HORA));
                String visualizacao = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.VISUALIZACAO));
                String nome_salao = cursor.getString(cursor.getColumnIndex(CriaBancoNotificacao.NOME_SALAO));
                conteudo_menssagem.add(new menssagem(id, titulo, texto, visualizacao,hora,nome_salao));
            }while(cursor.moveToNext());
        }
        cursor.close();
        lista.setAdapter(new Adaptador_notify(conteudo_menssagem,lista));
        lista.setLayoutManager(layout);




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                finish();
                break;
            default:break;
        }
        return true;
    }

}
