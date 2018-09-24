package com.stylehair.nerdsolutions.stylehair.Notification;

import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.Adaptador_notify;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.CriaBancoNotificacao;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Notificacoes;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.favorito.Adaptador_favorito;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class notificacao extends AppCompatActivity  {
    RecyclerView lista;
    int idLogin;
    Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_notificacoes);
        setSupportActionBar(myToolbar);

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        idLogin = getSharedPreferences.getInt("idLogin", -1);
        loading = new Loading(this);
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
        lista.setLayoutManager(layout);
        lista.setClickable(true);
        loading.abrir("Aguarde...");
        getNotificacoes();

       /* BancoNotifyController crud = new BancoNotifyController(getBaseContext());
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
        lista.setLayoutManager(layout);*/



    }


    public void getNotificacoes()
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Notificacoes>> callBuscaNotificacao = iApi.BuscaNotificacao(idLogin);
        callBuscaNotificacao.enqueue(new Callback<List<Notificacoes>>() {
            @Override
            public void onResponse(Call<List<Notificacoes>> call, Response<List<Notificacoes>> response) {
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        List<Notificacoes> ListaNotificacao = response.body();
                        lista.setAdapter(new adapter_notificacao(ListaNotificacao,lista));
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Notificacoes>> call, Throwable t) {
                    loading.fechar();
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent intent)
    {
        getNotificacoes();
    }
}
