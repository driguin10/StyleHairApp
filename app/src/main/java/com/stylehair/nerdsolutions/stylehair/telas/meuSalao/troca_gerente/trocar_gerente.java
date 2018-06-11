package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.troca_gerente;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.cadastrar_funcionario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class trocar_gerente extends AppCompatActivity {
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;
    public int qtTentativaUsuario= 0;
    TextInputLayout LoginEmail;
    TextInputLayout LoginSenha;
    Button pesquisaLogin;
    Button escolherUser;
    Loading loading;
    Button Limparcampos;
    int id_Login = -1;
    String id_Usuario="";
    Config config;
    CircleImageView imagem;
    TextView nomeUsuario;
    String idSalao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_gerente);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_trocaGerente);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Trocar gerencia");
        Drawable upArrow = ContextCompat.getDrawable(trocar_gerente.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(trocar_gerente.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            idSalao = bundle.getString("idSalao");
        }
        loading = new Loading(trocar_gerente.this);
        config = new Config();
        LoginEmail = (TextInputLayout) findViewById(R.id.procuraLoginEmail);
        LoginSenha = (TextInputLayout) findViewById(R.id.procuraLoginSenha);
        nomeUsuario = (TextView) findViewById(R.id.txtNomeUsuario);
        pesquisaLogin = (Button) findViewById(R.id.bt_pesquisaLogin);
        escolherUser = (Button) findViewById(R.id.bt_Confirmar);
        Limparcampos = (Button) findViewById(R.id.bt_limparLogin);
        imagem = (CircleImageView) findViewById(R.id.imgUsuario);
        pesquisaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampos()) {
                    loading.abrir("Aguarde...");
                    logar();
                }
            }
        });


        Limparcampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
                LoginEmail.getEditText().setText("");
                LoginSenha.getEditText().setText("");
                LoginEmail.getEditText().requestFocus();
                nomeUsuario.setText("");
            }
        });

        escolherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!id_Usuario.equals("")) {
                    Intent intent = new Intent(trocar_gerente.this, senha_gerente.class);
                    intent.putExtra("idSalao", idSalao);
                    intent.putExtra("idUsuario", id_Usuario);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Escolha um usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    public boolean verificaCampos(){
        Boolean status = false;
        String Email = LoginEmail.getEditText().getText().toString();
        String Senha =LoginSenha.getEditText().getText().toString();


        if(!Email.equals("") && !Senha.equals(""))
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                LoginEmail.getEditText().setError("Insira um email valido!!");
                LoginEmail.getEditText().requestFocus();
            }
            else
            if(Senha.length()<8)
            {
                LoginSenha.getEditText().requestFocus();
                LoginSenha.getEditText().setError("A senha deve ter o minimo de 8 caracteres !!");
                status=false;
            }
            else
            {
                status=true;
            }
        }
        else
        {
            if(Email.equals("")) {
                LoginEmail.getEditText().requestFocus();
                LoginEmail.getEditText().setError("Preencha este campo");
            }
            else
            if(Senha.equals("")) {
                LoginSenha.getEditText().requestFocus();
                LoginSenha.getEditText().setError("Preencha este campo");
            }

            status=false;
        }
        return status;
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

                    qtTentativaRealizada = 0;
                    Logar logar = response.body();

                    if(logar.login!=null)
                    {
                        for (Login log : logar.login) {
                            id_Login = log.getIdLogin();
                        }
                        if(!logar.getIdUser().equals(""))
                        {
                            pegarUsuario(Integer.valueOf(id_Login));
                        }else
                        {
                            loading.fechar();
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


    //---- função para pegar dados do usuario do servidor----
    public void pegarUsuario(final int idUsuario){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuario(idUsuario);
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                loading.fechar();
                callBuscaUser.cancel();
                switch (response.code()) {
                    case 200:
                        qtTentativaUsuario = 0;
                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        nomeUsuario.setText(user.getNome());

                        if (user.getLinkImagem() != "") {
                            Picasso.with(trocar_gerente.this).load(config.getWebService() + user.getLinkImagem()).into(imagem);
                        }
                        else
                        {
                            imagem.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
                        }

                        id_Usuario = String.valueOf(user.getIdUsuario());

                        break;


                    case 400:
                        if (response.message().equals("1")) {

                        }
                        if (response.message().equals("2")) {

                            //paramentros incorretos
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                if (qtTentativaUsuario < qtTentativas) {
                    qtTentativaUsuario++;
                    pegarUsuario(idUsuario);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
    //-------------------------------------------------------

}
