package com.stylehair.nerdsolutions.stylehair.telas.login;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.CaixaDialogo;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.classes.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cadastrar_login extends AppCompatActivity {
    private Button btCadastroLogin;
    CaixaDialogo caixaDialogo;
    private TextInputLayout txtCadEmail;
    private TextInputLayout txtCadSenha;
    private TextInputLayout txtCadRSenha;
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        caixaDialogo = new CaixaDialogo();

        txtCadEmail = (TextInputLayout) findViewById(R.id.cad_EditEmail);
        txtCadSenha = (TextInputLayout) findViewById(R.id.cad_EditSenha);
        txtCadRSenha = (TextInputLayout) findViewById(R.id.cad_EditRepSenha);

        btCadastroLogin = (Button) findViewById(R.id.bt_CadastraLogin);
        btCadastroLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificaConexao verificaConexao = new VerificaConexao();
                if(verificaConexao.verifica(cadastrar_login.this)) {
                 if(verificaCampos())
                 {
                     caixaDialogo.MenssagemDialog(cadastrar_login.this,"Aguarde...Enviando dados !!!");
                     cadastraLogin();
                 }
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
                }

                }//fim botao


        });


    }


    public void cadastraLogin(){
        Login login = new Login();
        login.setEmail(txtCadEmail.getEditText().getText().toString());
        login.setSenha(txtCadSenha.getEditText().getText().toString());
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callCadastroLogin = iApi.cadastraLogin(login);
        callCadastroLogin.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                caixaDialogo.fecharCaixa();


                    qtTentativaRealizada = 0;

                    switch (response.code()){
                        case 204:
                            Toast.makeText(cadastrar_login.this, "Cadastrado com sucesso !!", Toast.LENGTH_LONG).show();
                            finish();
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "03":
                                    Toast.makeText(cadastrar_login.this, "Erro ao cadastrar !! ", Toast.LENGTH_LONG).show();
                                    break;

                                case "02":
                                    Toast.makeText(cadastrar_login.this, "Parametros incorretos !!", Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;

                        case 403:
                            Toast.makeText(cadastrar_login.this, "usuario já existe !! ", Toast.LENGTH_LONG).show();
                            txtCadEmail.getEditText().requestFocus();
                            Log.d("xex","psd");
                            break;
                    }
                callCadastroLogin.cancel();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    cadastraLogin();
                } else {
                    caixaDialogo.fecharCaixa();
                    Log.d("xex",call.request().toString());
                }
            }
        });
    }

    public boolean verificaCampos(){
        Boolean status = false;
        String Email = txtCadEmail.getEditText().getText().toString();
        String Senha =txtCadSenha.getEditText().getText().toString();
        String Rsenha=txtCadRSenha.getEditText().getText().toString();

        if(!Email.equals("") && !Senha.equals("") && !Rsenha.equals(""))
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                txtCadEmail.getEditText().setError("Insira um email valido!!");
                txtCadEmail.getEditText().requestFocus();
            }
            else
            if(Senha.length()>=8)
            {
                if(Rsenha.equals(Senha))
                {
                    status = true;
                }
                else
                {
                    status =false;
                    txtCadRSenha.getEditText().setError("As senhas não são iguais !!");
                    txtCadRSenha.getEditText().requestFocus();
                }
            }
            else
            {
                txtCadSenha.getEditText().requestFocus();
                txtCadSenha.getEditText().setError("A senha deve ter o minimo de 8 caracteres !!");
                status=false;
            }
        }
        else
        {
            if(Email.equals("")) {
                txtCadEmail.getEditText().requestFocus();
                txtCadEmail.getEditText().setError("Preencha este campo");
            }
            else
            if(Senha.equals("")) {
                txtCadSenha.getEditText().requestFocus();
                txtCadSenha.getEditText().setError("Preencha este campo");
            }
            else
            if(Rsenha.equals("")) {
                txtCadRSenha.getEditText().requestFocus();
                txtCadRSenha.getEditText().setError("Preencha este campo");
            }
            status=false;
        }
        return status;
    }
}
