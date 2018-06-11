package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.APICepService;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Image;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Mask;
import com.stylehair.nerdsolutions.stylehair.auxiliar.UfToName;
import com.stylehair.nerdsolutions.stylehair.classes.CadastroFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEP;
import com.stylehair.nerdsolutions.stylehair.telas.cadastroSalao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentUsuario;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Button Limparcampos;

    ImageButton BtPesqData;
    ImageButton BtExcluirImagem;
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;

    public int qtTentativaRealizadaLoad = 0;
    public int qtTentativaRealizadaSalvar1 = 0;
    public int qtTentativaRealizadaSalvar2 = 0;
    public int qtTentativaRealizadaSalvar3 = 0;

    Config config;



    AlertDialog alerta;
    static final int imagem_interna = 1;
    static final int imagem_camera = 0;
    String LinkImagem = "";
    String filepath; // caminho da imagem
    String img64 =""; // base64 da imagem
    String tipoImagem=""; // extensao da imagem
    int percentImgArq = 20; //compressao da imagem vinda do arquivo interno
    int percentImgCam = 99; //compressao da imagem vinda do arquivo interno
    ScrollView scrollView;
    String tipoSalvar;

    int id_Login = -1;
    int id_Salao = -1 ;
    int id_Usuario = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_funcionario);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_cadastro_funcionario);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastrar Funcionário");
        Drawable upArrow = ContextCompat.getDrawable(cadastrar_funcionario.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(cadastrar_funcionario.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        loading = new Loading(cadastrar_funcionario.this);
        config = new Config();

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        id_Salao = Integer.valueOf(getSharedPreferences.getString("idSalao", ""));
       LoginEmail = (TextInputLayout) findViewById(R.id.procuraLoginEmail);
       LoginSenha = (TextInputLayout) findViewById(R.id.procuraLoginSenha);
       pesquisaLogin = (Button) findViewById(R.id.bt_pesquisaLogin);
        Limparcampos = (Button) findViewById(R.id.bt_limparLogin);
       pesquisaLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loading.abrir("Aguarde...");
               logar();
           }
       });

        Limparcampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmail.getEditText().setText("");
                LoginEmail.setAlpha(.9f);
                LoginEmail.setEnabled(true);

                LoginSenha.getEditText().setText("");
                LoginSenha.setAlpha(.9f);
                LoginSenha.setEnabled(true);

                cadNomeUser.getEditText().setText("");
                cadNomeUser.setAlpha(.9f);
                cadNomeUser.setEnabled(true);

                emailNovo.getEditText().setText("");
                senhaNova.getEditText().setText("");

                cadApelidoUser.getEditText().setText("");
                cadApelidoUser.setEnabled(true);
                cadApelidoUser.setAlpha(.9f);

                cadTelefoneUser.getEditText().setText("");
                cadTelefoneUser.setEnabled(true);
                cadTelefoneUser.setAlpha(.9f);

                cadEnderecoUser.getEditText().setText("");
                cadEnderecoUser.setEnabled(true);
                cadEnderecoUser.setAlpha(.9f);

                cadBairroUser.getEditText().setText("");
                cadBairroUser.setEnabled(true);
                cadBairroUser.setAlpha(.9f);

                cadNumeroUser.getEditText().setText("");
                cadNumeroUser.setEnabled(true);
                cadNumeroUser.setAlpha(.9f);

                cadCidadeUser.getEditText().setText("");
                cadCidadeUser.setEnabled(true);
                cadCidadeUser.setAlpha(.9f);

                cadObsUser.getEditText().setText("");
                cadObsUser.setEnabled(true);
                cadObsUser.setAlpha(.9f);

                cadCepUser.getEditText().setText("");
                cadCepUser.setEnabled(true);
                cadCepUser.setAlpha(.9f);

                cadNascimento.getEditText().setText("");
                cadNascimento.setAlpha(.9f);
                cadNascimento.setEnabled(true);

                cadEstadoUser.setEnabled(true);
                cadEstadoUser.setAlpha(.9f);


                cadSexoUser.setEnabled(true);
                cadSexoUser.setAlpha(.9f);

               BtCarregaImagem.setEnabled(true);
               BtCarregaImagem.setAlpha(.9f);

               BtExcluirImagem.setAlpha(.9f);
               BtExcluirImagem.setEnabled(true);


                BtPesqData.setEnabled(true);
                BtPesqData.setAlpha(.9f);


                emailNovo.setEnabled(true);
                emailNovo.setAlpha(.9f);

                senhaNova.setAlpha(.9f);
                senhaNova.setEnabled(true);







            }
        });

       scrollView = (ScrollView)findViewById(R.id.scrollCadastroFunc);





        cadNomeUser = (TextInputLayout) findViewById(R.id.txt_NomeNovo);
        emailNovo = (TextInputLayout) findViewById(R.id.txt_EmailNovo);
        senhaNova = (TextInputLayout) findViewById(R.id.txt_SenhaNova);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo(0, LoginSenha.getBottom());

            }
        });

        cadApelidoUser = (TextInputLayout) findViewById(R.id.txt_ApelidoNovo);
        cadTelefoneUser = (TextInputLayout) findViewById(R.id.txt_TelefoneNovo);
        cadEnderecoUser = (TextInputLayout) findViewById(R.id.txt_EnderecoNovo);
        cadBairroUser = (TextInputLayout)findViewById(R.id.txt_BairroNovo);
        cadNumeroUser = (TextInputLayout)findViewById(R.id.txt_NumeroNovo);
        cadEstadoUser = (Spinner) findViewById(R.id.Sp_EstadoNovo);
        cadCidadeUser = (TextInputLayout) findViewById(R.id.txt_CidadeNovo);
        cadObsUser = (TextInputLayout) findViewById(R.id.txt_ObsNovo);
        cadCepUser = (TextInputLayout) findViewById(R.id.txt_CepNovo);
        cadNascimento = (TextInputLayout) findViewById(R.id.txt_nascimentoNovo);
        cadSexoUser = (Spinner) findViewById(R.id.spn_SexoNovo);
        BtExcluirImagem = (ImageButton)findViewById(R.id.bt_excluiImgNovo);
        BtPesqData = (ImageButton) findViewById(R.id.pesquisa_dataNovo);
        BtCarregaImagem = (Button) findViewById(R.id.bt_caregaImgNovo);
        BtSalvar = (Button)findViewById(R.id.bt_salvarNovo);
        ImagemUser = (CircleImageView) findViewById(R.id.imagemUserNovo);

        //---------------- adiciona as mascaras no Telefone-Cep-Data --------------------------------------------------
        cadTelefoneUser.getEditText().addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, cadTelefoneUser.getEditText()));
        cadCepUser.getEditText().addTextChangedListener(Mask.insert(Mask.CEP_MASK, cadCepUser.getEditText()));
        cadNascimento.getEditText().addTextChangedListener(Mask.insert(Mask.DATA_MASK, cadNascimento.getEditText()));
        //---------------------------------------------------------------------------------------------------------------

        cadCepUser.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    String cep = cadCepUser.getEditText().getText().toString();
                    String novoCep = cep.trim();
                    novoCep = cep.replace("-", "");

                    if(novoCep.length() == 8) {
                        loading.abrir("Aguarde...");
                        pegarCep(novoCep);
                    }
                }
            }
        });


        BtSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /* Log.d("xex","idSalao - "+ String.valueOf(id_Salao));
                Log.d("xex","idUsuario - "+ String.valueOf(id_Usuario));
                Log.d("xex","idLogin - "+ String.valueOf(id_Login));*/
                if(!verificaCampos())
                {
                    Toast.makeText(cadastrar_funcionario.this, "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
                }
                else {
                    loading.abrir("Aguarde...");
                    if (id_Login == -1 && id_Usuario == -1) {
                        //tipo 3 -- não tem nenhum cadastro
                        salvar3();
                    } else if (id_Login != -1 && id_Usuario == -1) {
                        // tipo 2 tem só login
                        salvar2();
                    } else if (id_Login != -1 && id_Usuario != -1) {
                        // tipo 1 tem tudo
                        salvar1();
                    }
                }



            }
        });

        BtCarregaImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaImagem();
            }
        });

        //---- abre calendario para escolher data de nascimento ---------
        BtPesqData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        //----- exclui a imagem selecionada do usuario-------------------
        BtExcluirImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img64 = "";
                tipoImagem="";
                LinkImagem = "";
                ImagemUser.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
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


    public void pegarCep(String cep)
    {
        APICepService apiCepService = APICepService.retrofit.create(APICepService.class);
        final Call<CEP> callBuscaCep = apiCepService.getEnderecoByCEP(cep);
        callBuscaCep.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                loading.fechar();
                if (!response.isSuccessful()) {

                } else {
                    CEP cep = response.body();
                    cadEnderecoUser.getEditText().setText(cep.getLogradouro());
                    cadBairroUser.getEditText().setText(cep.getBairro());
                    cadCidadeUser.getEditText().setText(cep.getLocalidade());

                    if(cep.getUf()!=null) {
                        UfToName ufToName = new UfToName();
                        for (int i = 0; i < cadEstadoUser.getAdapter().getCount(); i++) {
                            if (cadEstadoUser.getAdapter().getItem(i).toString().contains(ufToName.estado(cep.getUf()))) {
                                cadEstadoUser.setSelection(i);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                loading.fechar();
                Log.d("xex", "erro no cep");
            }
        });
    }

    public void salvar1(){
        RequestBody TipoSalvar = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody idSalao = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Salao));
        RequestBody idUsuario = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Usuario) );

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<CadastroFuncionario> callCriaFunc1 = iApi.CriarFuncionario1(TipoSalvar,idSalao,idUsuario);
        callCriaFunc1.enqueue(new Callback<CadastroFuncionario>() {
            @Override
            public void onResponse(Call<CadastroFuncionario> call, Response<CadastroFuncionario> response) {
                loading.fechar();
                qtTentativaRealizadaSalvar1 = 0;
                callCriaFunc1.cancel();

                CadastroFuncionario ListcadFunc = response.body();
                if(response.isSuccessful())
                {
                    if(ListcadFunc.getId().equals(""))
                    {
                        Toast.makeText(cadastrar_funcionario.this,"erro ao salvar !!" ,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(!ListcadFunc.getId().equals("-1")) {
                             Toast.makeText(cadastrar_funcionario.this,"Salvo com sucesso !!! ",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(cadastrar_funcionario.this, ver_funcionario.class);
                            intent.putExtra("idUsuario", String.valueOf(id_Usuario));
                            intent.putExtra("idFuncionario", ListcadFunc.getId());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CadastroFuncionario> call, Throwable t) {

                if (qtTentativaRealizadaSalvar1 < qtTentativas) {
                    qtTentativaRealizadaSalvar1++;
                    salvar1();
                } else {
                    loading.fechar();
                }
            }
        });
    }

    public void salvar2(){
        RequestBody TipoSalvar = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody idSalao = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Salao));
        RequestBody idLogin = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Login) );

        RequestBody nome = RequestBody.create(MediaType.parse("text/plain"), cadNomeUser.getEditText().getText().toString());
        RequestBody apelido = RequestBody.create(MediaType.parse("text/plain"), cadApelidoUser.getEditText().getText().toString());
        RequestBody telefone = RequestBody.create(MediaType.parse("text/plain"), cadTelefoneUser.getEditText().getText().toString());
        RequestBody cep = RequestBody.create(MediaType.parse("text/plain"), cadCepUser.getEditText().getText().toString());
        RequestBody endereco = RequestBody.create(MediaType.parse("text/plain"), cadEnderecoUser.getEditText().getText().toString());
        RequestBody numero = RequestBody.create(MediaType.parse("text/plain"), cadNumeroUser.getEditText().getText().toString());
        RequestBody bairro = RequestBody.create(MediaType.parse("text/plain"), cadBairroUser.getEditText().getText().toString());
        RequestBody estado = RequestBody.create(MediaType.parse("text/plain"), cadEstadoUser.getSelectedItem().toString());
        RequestBody sexo = RequestBody.create(MediaType.parse("text/plain"), cadSexoUser.getSelectedItem().toString());
        RequestBody cidade = RequestBody.create(MediaType.parse("text/plain"), cadCidadeUser.getEditText().getText().toString());
        RequestBody obs = RequestBody.create(MediaType.parse("text/plain"), cadObsUser.getEditText().getText().toString());
        RequestBody dataNascimento = RequestBody.create(MediaType.parse("text/plain"), cadNascimento.getEditText().getText().toString());
        RequestBody mine = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        RequestBody converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), "");

        if (!tipoImagem.equals("") && !img64.equals("")) {
            mine = RequestBody.create(MediaType.parse("multipart/form-data"), tipoImagem);
            converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), img64);
        }

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<CadastroFuncionario> callCriaFunc2 = iApi.CriarFuncionario2(TipoSalvar,idSalao,converter64,mine,idLogin,nome, apelido, sexo, dataNascimento, telefone, cep, endereco, numero, bairro, estado, cidade, obs);
        callCriaFunc2.enqueue(new Callback<CadastroFuncionario>() {
            @Override
            public void onResponse(Call<CadastroFuncionario> call, Response<CadastroFuncionario> response) {
                loading.fechar();
                qtTentativaRealizadaSalvar2 = 0;
                callCriaFunc2.cancel();

                CadastroFuncionario ListcadFunc = response.body();
                if(response.isSuccessful())
                {
                    String[] ids = ListcadFunc.getId().split("#");
                    String iduser = ids[0];
                    String idfunc = ids[1];

                    if(iduser.equals("-1") && iduser.equals("-2") && idfunc.equals("-1"))
                    {
                        Toast.makeText(cadastrar_funcionario.this,"erro ao salvar !!" ,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                            Toast.makeText(cadastrar_funcionario.this,"Salvo com sucesso !!! " ,Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(cadastrar_funcionario.this, ver_funcionario.class);
                            intent.putExtra("idUsuario",iduser);
                            intent.putExtra("idFuncionario",idfunc);
                            startActivity(intent);
                            finish();


                    }
                }
            }

            @Override
            public void onFailure(Call<CadastroFuncionario> call, Throwable t) {

                if (qtTentativaRealizadaSalvar2 < qtTentativas) {
                    qtTentativaRealizadaSalvar2++;
                    salvar2();
                } else {
                    loading.fechar();
                }
            }
        });
    }

    public void salvar3(){
        RequestBody TipoSalvar = RequestBody.create(MediaType.parse("text/plain"), "3");
        RequestBody idSalao = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Salao));
        RequestBody idLogin = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_Login) );


        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), emailNovo.getEditText().getText().toString());
        RequestBody senha = RequestBody.create(MediaType.parse("text/plain"), senhaNova.getEditText().getText().toString());

        RequestBody nome = RequestBody.create(MediaType.parse("text/plain"), cadNomeUser.getEditText().getText().toString());
        RequestBody apelido = RequestBody.create(MediaType.parse("text/plain"), cadApelidoUser.getEditText().getText().toString());
        RequestBody telefone = RequestBody.create(MediaType.parse("text/plain"), cadTelefoneUser.getEditText().getText().toString());
        RequestBody cep = RequestBody.create(MediaType.parse("text/plain"), cadCepUser.getEditText().getText().toString());
        RequestBody endereco = RequestBody.create(MediaType.parse("text/plain"), cadEnderecoUser.getEditText().getText().toString());
        RequestBody numero = RequestBody.create(MediaType.parse("text/plain"), cadNumeroUser.getEditText().getText().toString());
        RequestBody bairro = RequestBody.create(MediaType.parse("text/plain"), cadBairroUser.getEditText().getText().toString());
        RequestBody estado = RequestBody.create(MediaType.parse("text/plain"), cadEstadoUser.getSelectedItem().toString());
        RequestBody sexo = RequestBody.create(MediaType.parse("text/plain"), cadSexoUser.getSelectedItem().toString());
        RequestBody cidade = RequestBody.create(MediaType.parse("text/plain"), cadCidadeUser.getEditText().getText().toString());
        RequestBody obs = RequestBody.create(MediaType.parse("text/plain"), cadObsUser.getEditText().getText().toString());
        RequestBody dataNascimento = RequestBody.create(MediaType.parse("text/plain"), cadNascimento.getEditText().getText().toString());
        RequestBody mine = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        RequestBody converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), "");

        if (!tipoImagem.equals("") && !img64.equals("")) {
            mine = RequestBody.create(MediaType.parse("multipart/form-data"), tipoImagem);
            converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), img64);
        }

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<CadastroFuncionario> callCriaFunc3 = iApi.CriarFuncionario3(TipoSalvar,idSalao,email,senha,converter64,mine,idLogin,nome, apelido, sexo, dataNascimento, telefone, cep, endereco, numero, bairro, estado, cidade, obs);
        callCriaFunc3.enqueue(new Callback<CadastroFuncionario>() {
            @Override
            public void onResponse(Call<CadastroFuncionario> call, Response<CadastroFuncionario> response) {
                loading.fechar();
                qtTentativaRealizadaSalvar3 = 0;
                callCriaFunc3.cancel();


                CadastroFuncionario ListcadFunc = response.body();

                if(response.isSuccessful())
                {
                    String[] ids = ListcadFunc.getId().split("#");
                    String iduser = ids[0];
                    String idfunc = ids[1];

                    if(iduser.equals("-2")) {
                        Toast.makeText(cadastrar_funcionario.this,"Email já existe !!" ,Toast.LENGTH_LONG).show();
                        emailNovo.getEditText().requestFocus();
                    }
                    else
                    if(iduser.equals("-1") && idfunc.equals("-1"))
                    {
                        Toast.makeText(cadastrar_funcionario.this,"erro ao salvar !!" ,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(cadastrar_funcionario.this,"Salvo com sucesso !!! " ,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(cadastrar_funcionario.this, ver_funcionario.class);
                        intent.putExtra("idUsuario",iduser);
                        intent.putExtra("idFuncionario",idfunc);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<CadastroFuncionario> call, Throwable t) {

                if (qtTentativaRealizadaSalvar3 < qtTentativas) {
                    qtTentativaRealizadaSalvar3++;
                    salvar3();
                } else {
                    loading.fechar();
                }
            }
        });
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

                        String Email = "";
                        for (Login log : logar.login) {
                            Email = log.getEmail();
                            id_Login = log.getIdLogin();
                        }

                        emailNovo.getEditText().setText(Email);
                        emailNovo.setEnabled(false);
                        emailNovo.setClickable(false);
                        emailNovo.setAlpha(.4f);
                        senhaNova.getEditText().setText(login.getSenha());
                        senhaNova.setClickable(false);
                        senhaNova.setEnabled(false);
                        senhaNova.setAlpha(.4f);


                        if(!logar.getIdUser().equals(""))
                        {
                            pegarUsuario(Integer.valueOf(id_Login));
                        }else
                        {
                            loading.fechar();
                        }


                       /* String NomeUsuario = logar.getNomeUser();
                        String linkImagem = logar.getLinkImagem();
                        cadNomeUser.getEditText().setText(NomeUsuario);

                        if (linkImagem != "") {
                            Picasso.with(cadastrar_funcionario.this).load(config.getWebService() + linkImagem).into(ImagemUser);
                        }*/
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
                        qtTentativaRealizadaLoad = 0;


                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        cadNomeUser.getEditText().setText(user.getNome());
                        cadNomeUser.setAlpha(.4f);
                        cadNomeUser.setEnabled(false);

                        cadApelidoUser.getEditText().setText(user.getApelido());
                        cadApelidoUser.setEnabled(false);
                        cadApelidoUser.setAlpha(.4f);

                        cadTelefoneUser.getEditText().setText(user.getTelefone());
                        cadTelefoneUser.setEnabled(false);
                        cadTelefoneUser.setAlpha(.4f);

                        cadEnderecoUser.getEditText().setText(user.getEndereco());
                        cadEnderecoUser.setEnabled(false);
                        cadEnderecoUser.setAlpha(.4f);

                        cadBairroUser.getEditText().setText(user.getBairro());
                        cadBairroUser.setEnabled(false);
                        cadBairroUser.setAlpha(.4f);

                        cadNumeroUser.getEditText().setText(String.valueOf(user.getNumero()));
                        cadNumeroUser.setEnabled(false);
                        cadNumeroUser.setAlpha(.4f);

                        cadCidadeUser.getEditText().setText(user.getCidade());
                        cadCidadeUser.setEnabled(false);
                        cadCidadeUser.setAlpha(.4f);

                        cadObsUser.getEditText().setText(user.getObs());
                        cadObsUser.setEnabled(false);
                        cadObsUser.setAlpha(.4f);

                        cadCepUser.getEditText().setText(user.getCep());
                        cadCepUser.setEnabled(false);
                        cadCepUser.setAlpha(.4f);




                        for(int i= 0; i < cadEstadoUser.getAdapter().getCount(); i++)
                        {
                            if(cadEstadoUser.getAdapter().getItem(i).toString().contains(user.getEstado()))
                            {
                                cadEstadoUser.setSelection(i);
                                cadEstadoUser.setEnabled(false);
                                cadEstadoUser.setAlpha(.4f);
                            }
                        }

                        for(int i= 0; i < cadSexoUser.getAdapter().getCount(); i++)
                        {
                            if(cadSexoUser.getAdapter().getItem(i).toString().contains(user.getSexo()))
                            {
                                cadSexoUser.setSelection(i);
                                cadSexoUser.setEnabled(false);
                                cadSexoUser.setAlpha(.4f);
                            }
                        }

                        if (user.getLinkImagem() != "") {
                            Picasso.with(cadastrar_funcionario.this).load(config.getWebService() + user.getLinkImagem()).into(ImagemUser);
                        }

                        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dataFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                        String outputNascimento = "";
                        try {
                            outputNascimento = dataFormat2.format(dataFormat.parse(user.getDataNascimento()));
                        } catch (ParseException p) {
                        }

                        cadNascimento.getEditText().setText(outputNascimento);
                        cadNascimento.setEnabled(false);
                        cadNascimento.setAlpha(.4f);

                        BtCarregaImagem.setEnabled(false);
                        BtCarregaImagem.setAlpha(.4f);

                        BtExcluirImagem.setEnabled(false);
                        BtExcluirImagem.setAlpha(.4f);

                        BtPesqData.setEnabled(false);
                        BtPesqData.setAlpha(.4f);

                        id_Usuario = Integer.valueOf(user.getIdUsuario());

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
                if (qtTentativaRealizadaLoad < qtTentativas) {
                    qtTentativaRealizadaLoad++;
                    pegarUsuario(Integer.valueOf(id_Login));
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
    //-------------------------------------------------------


    //------ opcões para escolher imagem---
    public void EscolhaImagem(){
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.activity_dialog_escolher_imagem, null);

        view.findViewById(R.id.bt_cancela_dialog).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alerta.dismiss();
            }
        });

        view.findViewById(R.id.bt_get_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

                startActivityForResult(intent,imagem_camera);

            }
        });

        view.findViewById(R.id.bt_get_galeria).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, imagem_interna);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(cadastrar_funcionario.this);
        builder.setView(view);
        alerta = builder.create();
        alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alerta.show();

    }
    //-------------------------------------




    public boolean verificaCampos()
    {
        Boolean status = false;
        String Vnome =cadNomeUser.getEditText().getText().toString();
        String Vtelefone = cadTelefoneUser.getEditText().getText().toString();
        String Vestado = cadEstadoUser.getSelectedItem().toString();
        String Vcidade = cadCidadeUser.getEditText().getText().toString();

        String Vemail  = emailNovo.getEditText().getText().toString();
        String Vsenha = senhaNova.getEditText().getText().toString();


        if(!Vnome.equals("") && !Vtelefone.equals("") && !Vestado.equals("") && !Vcidade.equals("") && !Vemail.equals("") && !Vsenha.equals(""))
        {
            status = true;
        }
        else
        {
            if(Vnome.equals(""))
                cadNomeUser.getEditText().requestFocus();
            else
            if(Vtelefone.equals(""))
                cadTelefoneUser.getEditText().requestFocus();
            else
            if(Vestado.equals(""))
                cadEstadoUser.requestFocus();
            else
            if(Vcidade.equals(""))
                cadCidadeUser.getEditText().requestFocus();
            else
            if(Vemail.equals(""))
                emailNovo.getEditText().requestFocus();
            else
            if(Vsenha.equals(""))
                senhaNova.getEditText().requestFocus();

        }
        return  status;
    }

    //--------- quando escolhe uma imagem---------------------------------
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {

        alerta.dismiss();
        if(resultCode!=0)
            loading.abrir("Aguarde...");

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case imagem_interna:
                if (resultCode == RESULT_OK) {
                    Image img = new Image();
                    ContentResolver cResolver =cadastrar_funcionario.this.getContentResolver();
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = cResolver.query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filepath = cursor.getString(columnIndex);
                    cursor.close();

                    File f64 = new File(filepath);
                    if(f64!=null)
                    {
                        img.setResizedBitmap(f64,percentImgArq);
                        img.setMimeFromImgPath(f64.getPath());
                    }
                    img64  = img.getBitmapBase64();
                    tipoImagem = img.getMime();


                    if(img.getBitmap()!=null)
                    {
                        ImagemUser.setImageBitmap(img.getBitmap());
                    }
                    loading.fechar();
                }
                break;

            case imagem_camera:
                if (resultCode == RESULT_OK)
                {
                    Image img = new Image();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    Uri uri = img.getImageUri(cadastrar_funcionario.this, photo);
                    File file = new File(img.getRealPathFromURI(cadastrar_funcionario.this,uri));
                    String pathImgCamera = file.getPath();
                    File f64 = new File(pathImgCamera);
                    if(f64!=null)
                    {
                        img.setResizedBitmap(f64,percentImgCam);
                        img.setMimeFromImgPath(f64.getPath());
                    }
                    img64  = img.getBitmapBase64();
                    tipoImagem = img.getMime();

                    if(img.getBitmap()!=null)
                    {
                        ImagemUser.setImageBitmap(img.getBitmap());
                    }
                    loading.fechar();

                }
                break;

            default:
                loading.fechar();
                break;
        }//fim switch

    }
    //----------------------------------------------------------------------


    //--------- calendario para data de nascimento--------------
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),R.style.MyDatePickerDialogTheme,this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String DataNasc = String.valueOf(day)+"-"+String.valueOf(month +1)+"-" +String.valueOf(year);
            SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
            String output =  "";

            try {
                output = dataFormat.format(dataFormat.parse(DataNasc));
            }catch (ParseException p) { }

            ((TextInputLayout) getActivity().findViewById(R.id.txt_nascimentoNovo)).getEditText().setText(output);

        }
    }
    //------------------------------------------------------------
}
