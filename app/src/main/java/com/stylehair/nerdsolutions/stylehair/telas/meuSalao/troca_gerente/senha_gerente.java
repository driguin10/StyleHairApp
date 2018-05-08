package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.troca_gerente;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.telas.login.cadastrar_login;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class senha_gerente extends AppCompatActivity {

    String idSalao;
    String idUsuario;
    Button btTransferir;
    Button cancelar;
    TextInputLayout senha;
    TextInputLayout Rsenha;
    Loading loading;

    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;
    public int qtTentativaRealizadaT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha_gerente);
        loading = new Loading(senha_gerente.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            idSalao = bundle.getString("idSalao");
            idUsuario = bundle.getString("idUsuario");
        }
        senha = (TextInputLayout) findViewById(R.id.SenhaGerente);
        Rsenha = (TextInputLayout) findViewById(R.id.RSenhaGerente);
        btTransferir = (Button) findViewById(R.id.bt_transferir);

        cancelar = (Button) findViewById(R.id.bt_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampos())
                {
                    loading.abrir("Aguarde...Transferindo !!!");
                    logar();
                }
            }
        });
    }

    public boolean verificaCampos(){
        Boolean status = false;

        String Senha =senha.getEditText().getText().toString();
        String RSenha=Rsenha.getEditText().getText().toString();

        if(!Senha.equals("") && !RSenha.equals(""))
        {
            if(Senha.length()>=8)
            {
                if(RSenha.equals(Senha))
                {
                    status = true;
                }
                else
                {
                    status =false;
                    Rsenha.getEditText().setError("As senhas não são iguais !!");
                    Rsenha.getEditText().requestFocus();
                }
            }
            else
            {
                senha.getEditText().requestFocus();
                senha.getEditText().setError("A senha deve ter o minimo de 8 caracteres !!");
                status=false;
            }
        }
        else
        {
            if(Senha.equals("")) {
                senha.getEditText().requestFocus();
                senha.getEditText().setError("Preencha este campo");
            }
            else
            if(RSenha.equals("")) {
                Rsenha.getEditText().requestFocus();
                Rsenha.getEditText().setError("Preencha este campo");
            }
            status=false;
        }
        return status;
    }



    public void logar(){


        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        final Login login = new Login();
        login.setEmail(getSharedPreferences.getString("email", ""));
        login.setSenha(senha.getEditText().getText().toString());

        final IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<Logar> callLoga = iApi.Logar(login);
        callLoga.enqueue(new Callback<Logar>() {
            @Override
            public void onResponse(Call<Logar> call, Response<Logar> response) {
                callLoga.cancel();
                loading.fechar();
                if(response.isSuccessful()) {

                    qtTentativaRealizada = 0;
                    Logar logar = response.body();

                    if(logar.login!=null)
                    {
                        transferirGerente();
                    }
                }
                else
                {

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

                if(qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    logar();
                }
                else
                {
                    loading.fechar();
                    Toast.makeText(getBaseContext(), "Não foi possível fazer a conexão", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }




    public void transferirGerente(){
        RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"),idSalao);
        RequestBody IDUSUARIO = RequestBody.create(MediaType.parse("text/plain"),idUsuario);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callTrocaGerencia = iApi.TransferirGerente(IDSALAO,IDUSUARIO);
        callTrocaGerencia.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                loading.fechar();


                qtTentativaRealizadaT = 0;

                switch (response.code()){
                    case 204:
                        Logout logout = new Logout();
                        logout.deslogar(senha_gerente.this,false);
                        Toast.makeText(senha_gerente.this, "Você não é mais o gerente !!", Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "04":
                                Toast.makeText(senha_gerente.this, "Erro ao cadastrar !! ", Toast.LENGTH_LONG).show();
                                break;

                            case "02":
                                Toast.makeText(senha_gerente.this, "Parametros incorretos !!", Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;

                }
                callTrocaGerencia.cancel();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaT < qtTentativas) {
                    qtTentativaRealizadaT++;
                    transferirGerente();
                } else {
                    loading.fechar();
                    Log.d("xex",call.request().toString());
                }
            }
        });
    }
}
