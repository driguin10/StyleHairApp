package com.stylehair.nerdsolutions.stylehair.telas.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.TextInputLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.CaixaDialogo;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.telas.principal;
import com.stylehair.nerdsolutions.stylehair.telas.introducao.Introducao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class logar extends AppCompatActivity {
    public boolean isFirstStart;
    public boolean isLogado;
    Loading loading;
    Context mcontext;
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;
    public String emailUser;

    public TextInputLayout logEmail;
    public TextInputLayout logSenha;
    public Button btLogar;
    public Button btCadastrar;
    public TextView btEsqueci;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loading = new Loading(logar.this);
        logEmail = (TextInputLayout)findViewById(R.id.LoginEmail);
        logSenha = (TextInputLayout) findViewById(R.id.LoginSenha);
        btCadastrar = (Button) findViewById(R.id.bt_loginCadastrar);
        btLogar = (Button) findViewById(R.id.bt_loginLogar);
        btEsqueci = (TextView)findViewById(R.id.bt_esqueci_senha);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                SharedPreferences getSharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());


                isFirstStart = getSharedPreferences.getBoolean("firstStart", true);
                isLogado = getSharedPreferences.getBoolean("logado", false);

                if (isFirstStart) {
                    Intent i = new Intent(logar.this, Introducao.class);
                    startActivity(i);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                    e.commit();
                }
                else{
                    if (isLogado)
                    {
                         Intent i = new Intent(logar.this, principal.class);
                         startActivity(i);
                         finish();
                    }

                }
            }
        });
        t.start();



        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(logar.this, cadastrar_login.class);
                startActivity(i);
            }
        });

        btEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logar.this,esqueci_senha.class);
                startActivity(intent);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerificaConexao verificaConexao = new VerificaConexao();
                if(verificaConexao.verifica(logar.this)) {
                    if (verificaCampos()) {
                        loading.abrir("Aguarde...");
                        logar();
                    }
                }else
                {
                    Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public void logar(){

        Login login = new Login();
        login.setEmail(logEmail.getEditText().getText().toString());
        login.setSenha(logSenha.getEditText().getText().toString());

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<Logar> callLoga = iApi.Logar(login);
        callLoga.enqueue(new Callback<Logar>() {
            @Override
            public void onResponse(Call<Logar> call, Response<Logar> response) {
                callLoga.cancel();

                if(response.isSuccessful()) {
                    loading.fechar();
                    qtTentativaRealizada = 0;
                    Logar logar = response.body();

                    if(logar.login!=null) {
                        int idLogin = -1;
                        String Email = "";
                        String NomeUsuario = "";
                        String linkImagem = "";
                        String topico = "";
                        String idUsuario ="";
                        idUsuario = logar.getIdUser();
                        NomeUsuario = logar.getNomeUser();
                        linkImagem = logar.getLinkImagem();
                        topico = logar.getTopico();

                        for (Login log : logar.login) {
                            Email = log.getEmail();
                            idLogin = log.getIdLogin();
                        }

                        SharedPreferences getSharedPreferencesL = PreferenceManager
                                .getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor e = getSharedPreferencesL.edit();
                        e.putBoolean("logado", true);
                        e.putInt("idLogin", idLogin);
                        e.putString("email", Email);
                        e.putString("idUsuario",idUsuario);
                        e.putString("nomeUser",NomeUsuario);
                        e.putString("linkImagem",linkImagem);
                        e.putBoolean("firstStart", false);
                        e.putString("topicoNotificacao",topico);
                        e.apply();
                        e.commit();

                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.addTopico("AllNotifications");
                        if(!topico.equals("")) {
                            topicoNotificacao.addTopico(topico);
                        }

                        Intent i = new Intent(com.stylehair.nerdsolutions.stylehair.telas.login.logar.this, principal.class);
                        startActivity(i);
                        finish();

                    }
                }
                else
                {


                    loading.fechar();
                    switch (response.code()) {
                        case 400:
                            if(response.message().equals("01"))
                                Toast.makeText(getBaseContext(), "Usuario não encontrado", Toast.LENGTH_SHORT).show();
                            else
                            if(response.message().equals("02"))
                                Toast.makeText(getBaseContext(), "parametros incorretos", Toast.LENGTH_SHORT).show();
                            break;

                        case 401:
                            Toast.makeText(getBaseContext(), "não autorizado", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }





            }

            @Override
            public void onFailure(Call<Logar> call, Throwable t) {
                loading.fechar();
                if(qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    logar();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean verificaCampos(){
        Boolean status = false;
        String Email = logEmail.getEditText().getText().toString();
        String Senha =logSenha.getEditText().getText().toString();


        if(!Email.equals("") && !Senha.equals(""))
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                logEmail.getEditText().setError("Insira um email valido!!");
                logEmail.getEditText().requestFocus();
            }
            else
            if(Senha.length()>=8)
            {
                status = true;
            }
            else
            {
                logSenha.getEditText().requestFocus();
                logSenha.getEditText().setError("A senha deve ter o minimo de 8 caracteres !!");
                status=false;
            }
        }
        else
        {
            if(Email.equals("")) {
                logEmail.getEditText().requestFocus();
                logEmail.getEditText().setError("Preencha este campo");
            }
            else
            if(Senha.equals("")) {
                logSenha.getEditText().requestFocus();
                logSenha.getEditText().setError("Preencha este campo");
            }

            status=false;
        }
        return status;
    }




}
