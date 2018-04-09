package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cadastrar_funcionario extends AppCompatActivity {
TextInputLayout LoginEmail;
TextInputLayout LoginSenha;
Button pesquisaLogin;
Loading loading;

    CircleImageView ImagemUser;

    TextInputLayout cadNomeUser;
    TextInputLayout cadApelidoUser;
    TextInputLayout cadTelefoneUser;
    TextInputLayout cadCepUser;
    TextInputLayout cadEnderecoUser;
    TextInputLayout cadBairroUser;
    TextInputLayout cadNumeroUser;
    TextInputLayout cadCidadeUser;
    TextInputLayout cadObsUser;
    TextInputLayout cadNascimento;

    TextInputLayout emailNovo;

    TextInputLayout senhaNova;

    Spinner cadEstadoUser;
    Spinner cadSexoUser;

    Button BtSalvar;
    Button BtCarregaImagem ;

    ImageButton BtPesqData;
    ImageButton BtExcluirImagem;
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;

    Config config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_funcionario);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_cadastro_funcionario);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastrar Funcionário");
        loading = new Loading(cadastrar_funcionario.this);
config = new Config();
       LoginEmail = (TextInputLayout) findViewById(R.id.procuraLoginEmail);
       LoginSenha = (TextInputLayout) findViewById(R.id.procuraLoginSenha);
       pesquisaLogin = (Button) findViewById(R.id.bt_pesquisaLogin);
       pesquisaLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loading.abrir("Pesquisando Usuarios...");
               logar();
           }
       });


        cadNomeUser = (TextInputLayout) findViewById(R.id.txt_NomeNovo);
        emailNovo = (TextInputLayout) findViewById(R.id.txt_EmailNovo);
        senhaNova = (TextInputLayout) findViewById(R.id.txt_SenhaNova);
       /* cadApelidoUser = (TextInputLayout) findViewById(R.id.txt_usrApelido);
        cadTelefoneUser = (TextInputLayout) findViewById(R.id.txt_usrTelefone);
        cadEnderecoUser = (TextInputLayout) findViewById(R.id.txt_usrEndereco);
        cadBairroUser = (TextInputLayout)findViewById(R.id.txt_usrBairro);
        cadNumeroUser = (TextInputLayout)findViewById(R.id.txt_usrNumero);
        cadEstadoUser = (Spinner) findViewById(R.id.Sp_usrEstado);
        cadCidadeUser = (TextInputLayout) findViewById(R.id.txt_usrCidade);
        cadObsUser = (TextInputLayout) findViewById(R.id.txt_usrObs);
        cadCepUser = (TextInputLayout) findViewById(R.id.txt_usrCep);
        cadNascimento = (TextInputLayout) findViewById(R.id.txt_nascimento);
        cadSexoUser = (Spinner) findViewById(R.id.spn_usrSexo);
        BtExcluirImagem = (ImageButton)findViewById(R.id.bt_excluiImg);
        BtPesqData = (ImageButton) findViewById(R.id.pesquisa_data);
        BtCarregaImagem = (Button) findViewById(R.id.bt_caregaImg);
        BtSalvar = (Button)findViewById(R.id.bt_salvarUser);*/
        ImagemUser = (CircleImageView) findViewById(R.id.imagemUserNovo);
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




    public void logar(){

        final Login login = new Login();
        login.setEmail(LoginEmail.getEditText().getText().toString());
        login.setSenha(LoginSenha.getEditText().getText().toString());

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
                        NomeUsuario = logar.getNomeUser();
                        linkImagem = logar.getLinkImagem();

                        for (Login log : logar.login) {
                            Email = log.getEmail();
                            idLogin = log.getIdLogin();
                        }

                        cadNomeUser.getEditText().setText(NomeUsuario);
                        emailNovo.getEditText().setText(Email);
                        emailNovo.setEnabled(false);
                        emailNovo.setClickable(false);
                        emailNovo.setAlpha(.4f);
                        senhaNova.getEditText().setText(login.getSenha());
                        senhaNova.setClickable(false);
                        senhaNova.setEnabled(false);
                        senhaNova.setAlpha(.4f);

                        if (linkImagem != "") {
                            Picasso.with(cadastrar_funcionario.this).load(config.getWebService() + linkImagem).into(ImagemUser);
                        }
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
}
