package com.stylehair.nerdsolutions.stylehair.telas.login;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.CaixaDialogo;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class esqueci_senha extends AppCompatActivity {
    Loading loading;
    EditText emailReset;
    Button btEnviarSenha;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);
       loading = new Loading(esqueci_senha.this);
        emailReset = (EditText) findViewById(R.id.txt_email_recuperar);
        btEnviarSenha = (Button)findViewById(R.id.bt_enviarSenha);
        btEnviarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificaConexao verificaConexao = new VerificaConexao();
                if(verificaConexao.verifica(esqueci_senha.this)) {
                    if (verificaEmail()) {
                        loading.abrir("Aguarde...");
                        EnviaNotificacao();
                    }
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean verificaEmail(){
        boolean status = false;
        if(emailReset.getText().equals("") || !Patterns.EMAIL_ADDRESS.matcher(emailReset.getText()).matches())
        {
            emailReset.setError("Insira um email valido!!");
            emailReset.requestFocus();
            status=false;
        }
        else
        {
            status=true;
        }
        return status;
    }

    public void EnviaNotificacao(){
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailReset.getText().toString());
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callReset = iApi.esqueciSenha(email);
        callReset.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                if (response.isSuccessful()) {
                    qtTentativaRealizada = 0;

                    switch (response.code()){
                        case 204:
                            Toast.makeText(esqueci_senha.this, "Solicitação enviada !! Foi enviado um link para alterar a senha em seu email. ", Toast.LENGTH_LONG).show();
                            finish();
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "03":
                                    Toast.makeText(esqueci_senha.this, "Ops !! houve um erro ao solicitar alteração de senha !! ", Toast.LENGTH_LONG).show();
                                    break;

                                case "06":
                                    Toast.makeText(esqueci_senha.this, "Desculpe. Não encontramos este email !!", Toast.LENGTH_LONG).show();
                                    emailReset.requestFocus();
                                    break;

                                case "02":
                                    Toast.makeText(esqueci_senha.this, "Ops !! houve um erro, Parametros incorretos", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;
                    }
                }
                callReset.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    EnviaNotificacao();
                } else {
                    loading.fechar();
                }
            }
        });
    }
}
