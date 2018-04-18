package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.Notification.Notification;
import com.stylehair.nerdsolutions.stylehair.Notification.ReturnMessage;
import com.stylehair.nerdsolutions.stylehair.Notification.Sender;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.api.INotification;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.editar_salao;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notificacao extends AppCompatActivity {

    TextView enviarP;
    CardView btCLientes;
    CardView btFuncionarios;
    TextInputLayout tituloNot;
    TextInputLayout menssagemNotify;
    Button btEnviar;

    String topico;
    Loading loading;


    int qtTentativas = 3;
    int qtTentativaRealizadaSalvar = 0;

    String IdUsuario;
    String TopicoSalao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_EnviarNotificacoes);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Enviar Notificações");
        //-------pega o id do login para fazer a consulta---------------
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        IdUsuario = getSharedPreferences.getString("idUsuario", "");
        //-
        loading = new Loading(Notificacao.this);

        enviarP = (TextView) findViewById(R.id.txtEnviarP);
        btCLientes = (CardView) findViewById(R.id.card_btClientes);
        btFuncionarios = (CardView) findViewById(R.id.card_btFuncionarios);
        tituloNot = (TextInputLayout) findViewById(R.id.txt_mensTitulo);
        menssagemNotify = (TextInputLayout) findViewById(R.id.txt_mensTexto);
        btEnviar = (Button) findViewById(R.id.bt_enviarNotificacao);


        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit = tituloNot.getEditText().getText().toString();
                String men = menssagemNotify.getEditText().getText().toString();
                String nomeS = "teste";

             loading.abrir("Enviando, aguarde...");
                EnviarNotificacao(tit,men,nomeS,topico);
            }
        });

        btCLientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topico = TopicoSalao;
                enviarP.setText("Todos os Clientes");
            }
        });

        btFuncionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarP.setText("driguin_10@hotmail.com");
            }
        });
    }



    public void EnviarNotificacao(String titulo, String menssagem, String nomeSalao, String topico){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String tituloNotify = titulo ;
        String nomeSalaoMen = nomeSalao;
        String tituloMen = menssagem ;
        String horaMen = dateFormat.format(date);

        String saidaMen = nomeSalaoMen+"§"+tituloMen+"§"+horaMen;
        Notification notification = new Notification(saidaMen,tituloNotify);
        Sender sender = new Sender(notification, "/topics/"+topico);
        INotification iNotification = INotification.retrofit.create(INotification.class);
        iNotification.enviarNotificacao(sender).enqueue(new Callback<ReturnMessage>() {
            @Override
            public void onResponse(Call<ReturnMessage> call, Response<ReturnMessage> response) {
                Toast.makeText(Notificacao.this,"Notificação enviada !!",Toast.LENGTH_LONG).show();
               loading.fechar();
            }

            @Override
            public void onFailure(Call<ReturnMessage> call, Throwable t) {
                Toast.makeText(Notificacao.this,"erro",Toast.LENGTH_LONG).show();
                loading.fechar();
            }
        });
    }

    public void pegarSalao(final String idUsuario){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Salao>> callBuscaSalao = iApi.BuscaSalao(Integer.valueOf(idUsuario));
        callBuscaSalao.enqueue(new Callback<List<Salao>>() {
            @Override
            public void onResponse(Call<List<Salao>> call, Response<List<Salao>> response) {
                loading.fechar();
                callBuscaSalao.cancel();
                switch (response.code()) {
                    case 200:
                        List<Salao> saloes = response.body();
                        Salao salao = saloes.get(0);
                        TopicoSalao = salao.getTopicoNotificacao();
                        break;


                    case 400:
                        switch (response.message())
                        {
                            case "04":
                               // Toast.makeText(editar_salao.this, "Erra ao editar !!", Toast.LENGTH_LONG).show();
                                break;

                            case "02":
                               // Toast.makeText(editar_salao.this, "Parametros incorretos !!", Toast.LENGTH_LONG).show();
                                break;
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Salao>> call, Throwable t) {
                if (qtTentativaRealizadaSalvar < qtTentativas) {
                    qtTentativaRealizadaSalvar++;

                    pegarSalao(idUsuario);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
    //-------------------------------------------------------




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
