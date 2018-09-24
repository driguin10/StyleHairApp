package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
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
import com.stylehair.nerdsolutions.stylehair.Notification.Dataa;
import com.stylehair.nerdsolutions.stylehair.Notification.Notification;
import com.stylehair.nerdsolutions.stylehair.Notification.ReturnMessage;
import com.stylehair.nerdsolutions.stylehair.Notification.Sender;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.api.INotification;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.editar_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    int qtTentativaRealizadaUsuario = 0;
    int qtTentativaRealizadaSalao = 0;
    String IdUsuario,IdSalao;
    String IdUsuarioEscolhido;
    String TopicoSalao;
    String NomeSalao;
    String quem ="0";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_EnviarNotificacoes);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Enviar Notificações");
        Drawable upArrow = ContextCompat.getDrawable(Notificacao.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(Notificacao.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
                loading.abrir("Aguarde...");
               // EnviarNotificacao(tit,men,NomeSalao,topico);
                if(quem.equals("0"))
                    enviarNotificacao("0",IdSalao,tit,men,NomeSalao);
                else
                    enviarNotificacao("1",id,tit,men,NomeSalao);
            }
        });

        btCLientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topico = TopicoSalao;
                enviarP.setText("Todos os Clientes");
                quem = "0";
            }
        });

        btFuncionarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Notificacao.this,pesquisaFuncionario.class);
                startActivityForResult(intent,1);
            }
        });

        pegarSalao(IdUsuario);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String[] teste = data.getData().toString().split("#");
                enviarP.setText(teste[0]);
                IdUsuarioEscolhido = teste[1];
                pegarUsuario(Integer.valueOf(IdUsuarioEscolhido));
            }
        }
    }

    public void EnviarNotificacao(String titulo, String menssagem, String nomeSalao, String topico){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        String horaMen = dateFormat.format(date);
        Notification notification = new Notification(menssagem,titulo);
        Dataa dataa=new Dataa(nomeSalao,horaMen);
        Sender sender = new Sender(notification, "/topics/"+topico,dataa);
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

    public void enviarNotificacao(String quem,String id,String titulo, String menssagem, String nomeSalao){

        RequestBody QUEM = RequestBody.create(MediaType.parse("text/plain"), quem);
        RequestBody ID = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody TITULO = RequestBody.create(MediaType.parse("text/plain"), titulo);
        RequestBody MENSSAGEM = RequestBody.create(MediaType.parse("text/plain"), menssagem);
        RequestBody NOME_SALAO = RequestBody.create(MediaType.parse("text/plain"), nomeSalao);

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callEnvia = iApi.EnviarNotificacao(QUEM,ID,TITULO,MENSSAGEM,NOME_SALAO);
        callEnvia.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 204:
                        Toast.makeText(Notificacao.this,"Enviado...",Toast.LENGTH_SHORT).show();
                        loading.fechar();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
                        NomeSalao = salao.getNome();
                        IdSalao =String.valueOf(salao.getIdSalao());
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


    //---- função para pegar dados do usuario do servidor----
    public void pegarUsuario(final int id_Usuario){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuarioId(id_Usuario);
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                loading.fechar();
                callBuscaUser.cancel();
                switch (response.code()) {
                    case 200:
                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);

                        topico = user.getTopicoNotificacao();
                        quem = "1";
                        id = String.valueOf(user.getIdLogin());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (qtTentativaRealizadaUsuario < qtTentativas) {
                    qtTentativaRealizadaUsuario++;
                    pegarUsuario(Integer.valueOf(IdUsuarioEscolhido));
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
