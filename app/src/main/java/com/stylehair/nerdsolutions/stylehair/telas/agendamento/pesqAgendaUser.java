package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

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
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.APICepService;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Image;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Mask;
import com.stylehair.nerdsolutions.stylehair.auxiliar.UfToName;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.classes.IdNovoUsuario;
import com.stylehair.nerdsolutions.stylehair.classes.Logar;
import com.stylehair.nerdsolutions.stylehair.classes.Login;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEP;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolherServico;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.troca_gerente.senha_gerente;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.troca_gerente.trocar_gerente;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentUsuario;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;

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

public class pesqAgendaUser extends AppCompatActivity {
    public int qtTentativas = 3;
    public int qtTentativaRealizada = 0;
    public int qtTentativaUsuario= 0;
    int qtTentativaRealizadaSalvar = 0;
    AlertDialog alerta;
    static final int imagem_interna = 1;
    static final int imagem_camera = 0;
    CircleImageView imagem;
    TextInputLayout nomeUsuario;
    TextInputLayout cadApelidoUser;
    TextInputLayout cadTelefoneUser;
    TextInputLayout cadTelefoneUserAnonimo;
    TextInputLayout cadCepUser;
    TextInputLayout cadEnderecoUser;
    TextInputLayout cadBairroUser;
    TextInputLayout cadNumeroUser;
    TextInputLayout cadCidadeUser;
    TextInputLayout cadObsUser;
    TextInputLayout cadNascimento;
    Spinner cadEstadoUser;
    Spinner cadSexoUser;
    Button BtSalvar;
    Button BtCarregaImagem ;
    Button BTCadastrar;
    ImageButton BtPesqData;
    ImageButton BtExcluirImagem;
    TextInputLayout LoginEmail;
    TextInputLayout LoginSenha;
    TextInputLayout cadEmailNovo;
    TextInputLayout cadSenhaNova;
    ImageButton pesquisaLogin;
    Button escolherUser;
    Button btAnonimo;
    Button bt_cadAnonimo;
    Loading loading;
    ImageButton Limparcampos;
    int IdLogin;
    String filepath; // caminho da imagem
    String img64 =""; // base64 da imagem
    String tipoImagem=""; // extensao da imagem
    int percentImgArq = 99; //compressao da imagem vinda do arquivo interno
    int percentImgCam = 99; //compressao da imagem vinda do arquivo interno
    int id_Login = -1;
    String id_Usuario="";
    Config config;
    String LinkImagem = "";
    String idSalao;
    boolean flagCadastro, flagAnonimo= false;

    Drawable bitmapPadrao;//guarda a imagem padrao do usuario
    LinearLayout LayBtsImagem;
    LinearLayout LayCampos;
    SharedPreferences getSharedPreferences;
    VerificaConexao verificaConexao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesq_agenda_user);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_escolheUser);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Selecione o Usuário");
        Drawable upArrow = ContextCompat.getDrawable(pesqAgendaUser.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(pesqAgendaUser.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        verificaConexao = new VerificaConexao();
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            idSalao = bundle.getString("idSalao");
        }
        loading = new Loading(pesqAgendaUser.this);
        config = new Config();
        LoginEmail = (TextInputLayout) findViewById(R.id.procuraLoginEmail);
        LoginSenha = (TextInputLayout) findViewById(R.id.procuraLoginSenha);
        pesquisaLogin = (ImageButton) findViewById(R.id.bt_pesquisaLogin);
        escolherUser = (Button) findViewById(R.id.bt_Confirmar);
        btAnonimo = (Button) findViewById(R.id.bt_anonimo);
        bt_cadAnonimo = (Button) findViewById(R.id.bt_cadAnonimo);
        Limparcampos = (ImageButton) findViewById(R.id.bt_limparLogin);
        LayBtsImagem = (LinearLayout) findViewById(R.id.layout_bts_imagem);
        LayCampos = (LinearLayout) findViewById(R.id.layout_campos);
        cadEmailNovo = (TextInputLayout) findViewById(R.id.txt_EmailNovo);
        cadSenhaNova = (TextInputLayout) findViewById(R.id.txt_SenhaNova);
        nomeUsuario = (TextInputLayout) findViewById(R.id.txt_userNome);
        cadApelidoUser = (TextInputLayout) findViewById(R.id.txt_usrApelido);
        cadTelefoneUser = (TextInputLayout) findViewById(R.id.txt_usrTelefone);
        cadTelefoneUserAnonimo = (TextInputLayout) findViewById(R.id.txt_usrTelefoneAnonimo);
        cadEnderecoUser = (TextInputLayout) findViewById(R.id.txt_usrEndereco);
        cadBairroUser = (TextInputLayout) findViewById(R.id.txt_usrBairro);
        cadNumeroUser = (TextInputLayout) findViewById(R.id.txt_usrNumero);
        cadEstadoUser = (Spinner) findViewById(R.id.Sp_usrEstado);
        cadCidadeUser = (TextInputLayout) findViewById(R.id.txt_usrCidade);
        cadObsUser = (TextInputLayout) findViewById(R.id.txt_usrObs);
        cadCepUser = (TextInputLayout) findViewById(R.id.txt_usrCep);
        cadNascimento = (TextInputLayout) findViewById(R.id.txt_nascimento);
        cadSexoUser = (Spinner) findViewById(R.id.spn_usrSexo);
        BtExcluirImagem = (ImageButton) findViewById(R.id.bt_excluiImg);
        BtPesqData = (ImageButton) findViewById(R.id.pesquisa_data);
        BtCarregaImagem = (Button) findViewById(R.id.bt_caregaImg);
        BtSalvar = (Button) findViewById(R.id.bt_Cadastrar);
        imagem = (CircleImageView) findViewById(R.id.imgUsuario);
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
//---- abre calendario para escolher data de nascimento ---------
        BtPesqData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


        btAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagAnonimo) {
                    flagAnonimo =true;
                    flagCadastro=false;
                    nomeUsuario.setVisibility(View.VISIBLE);
                    cadTelefoneUserAnonimo.setVisibility(View.VISIBLE);
                    bt_cadAnonimo.setVisibility(View.VISIBLE);

                    LoginEmail.getEditText().setEnabled(false);
                    LoginSenha.getEditText().setEnabled(false);
                    pesquisaLogin.setEnabled(false);
                  //  pesquisaLogin.setAlpha(.4f);
                    Limparcampos.setEnabled(false);
                   // Limparcampos.setAlpha(.4f);
                    imagem.setVisibility(View.GONE);
                    LayBtsImagem.setVisibility(View.GONE);
                    LayCampos.setVisibility(View.GONE);
                    BtSalvar.setVisibility(View.VISIBLE);

                }
                else
                {
                    flagAnonimo =false;
                    flagCadastro=true;
                    nomeUsuario.setVisibility(View.GONE);
                    cadTelefoneUserAnonimo.setVisibility(View.GONE);
                    BtSalvar.setVisibility(View.GONE);
                    bt_cadAnonimo.setVisibility(View.GONE);
                    LoginEmail.getEditText().setEnabled(true);
                    LoginSenha.getEditText().setEnabled(true);
                    pesquisaLogin.setEnabled(true);
                   // pesquisaLogin.setAlpha(1);
                    Limparcampos.setEnabled(true);
                   // Limparcampos.setAlpha(1);

                }
            }
        });

        //----- exclui a imagem selecionada do usuario-------------------
        BtExcluirImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img64 = "";
                tipoImagem="";
                LinkImagem = "";
                imagem.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
            }
        });
        //----------- abre dialog para escolher imagem-------------------
        BtCarregaImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaImagem();
            }
        });
        bitmapPadrao = imagem.getDrawable();

        BtSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaConexao.verifica(pesqAgendaUser.this))
                {
                    loading.abrir("Aguarde...");
                    salvarUsuario();
                }
            }
        });

        BTCadastrar = (Button) findViewById(R.id.bt_cadastrar);
        BTCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flagCadastro)
                {
                    nomeUsuario.setVisibility(View.GONE);
                    cadTelefoneUserAnonimo.setVisibility(View.GONE);
                    bt_cadAnonimo.setVisibility(View.GONE);

                    LoginEmail.getEditText().setEnabled(false);
                    LoginSenha.getEditText().setEnabled(false);
                    pesquisaLogin.setEnabled(false);
                   // pesquisaLogin.setAlpha(.4f);
                    Limparcampos.setEnabled(false);
                   // Limparcampos.setAlpha(.4f);
                    escolherUser.setVisibility(View.GONE);
                    imagem.setVisibility(View.VISIBLE);
                    LayBtsImagem.setVisibility(View.VISIBLE);
                    nomeUsuario.setVisibility(View.VISIBLE);
                    LayCampos.setVisibility(View.VISIBLE);
                    flagCadastro = true;
                    flagAnonimo =false;
                    nomeUsuario.getEditText().setEnabled(true);
                }
                else
                {
                    cadTelefoneUserAnonimo.setVisibility(View.GONE);
                    bt_cadAnonimo.setVisibility(View.GONE);
                    LoginEmail.getEditText().setEnabled(true);
                    LoginSenha.getEditText().setEnabled(true);
                    pesquisaLogin.setEnabled(true);
                   // pesquisaLogin.setAlpha(1);
                    Limparcampos.setEnabled(true);
                   // Limparcampos.setAlpha(1);
                    imagem.setVisibility(View.GONE);
                    LayBtsImagem.setVisibility(View.GONE);
                    nomeUsuario.setVisibility(View.GONE);
                    LayCampos.setVisibility(View.GONE);
                    flagCadastro = false;
                    flagAnonimo =true;
                }
            }
        });

        bt_cadAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Aguarde...");
                if(nomeUsuario.getEditText().getText().toString().equals("") && cadTelefoneUserAnonimo.getEditText().getText().toString().equals(""))
                {
                    loading.fechar();
                    Toast.makeText(pesqAgendaUser.this, "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    RequestBody nome = RequestBody.create(MediaType.parse("text/plain"), nomeUsuario.getEditText().getText().toString());
                    RequestBody telefone = RequestBody.create(MediaType.parse("text/plain"), cadTelefoneUserAnonimo.getEditText().getText().toString());
                    IApi iApi = IApi.retrofit.create(IApi.class);
                    final Call<IdNovoUsuario> callSalvaUser = iApi.salvarUsuarioAnonimo(nome,telefone);
                    callSalvaUser.enqueue(new Callback<IdNovoUsuario>() {
                        @Override
                        public void onResponse(Call<IdNovoUsuario> call, Response<IdNovoUsuario> response) {
                            Log.d("xex",String.valueOf(response.code())+ " - "+ response.message());
                            loading.fechar();
                            if (response.isSuccessful()) {
                                switch (response.code())
                                {
                                    case 200:
                                        Toast.makeText(pesqAgendaUser.this, "Usuario Criado !! ", Toast.LENGTH_LONG).show();
                                        IdNovoUsuario idNovoUsuario = response.body();
                                        Intent intent = new Intent(pesqAgendaUser.this, escolherServico.class);
                                        intent.putExtra("idSalao", idSalao);
                                        SharedPreferences.Editor e = getSharedPreferences.edit();
                                        e.putString("idUserAgendamento",String.valueOf(idNovoUsuario.getIdUsuario()));
                                        e.apply();
                                        e.commit();
                                        startActivity(intent);
                                        break;
                                }
                            }
                            callSalvaUser.cancel();
                        }

                        @Override
                        public void onFailure(Call<IdNovoUsuario> call, Throwable t) {

                                loading.fechar();

                        }
                    });







                }

            }
        });
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
                nomeUsuario.getEditText().setText("");
                escolherUser.setVisibility(View.GONE);
                nomeUsuario.getEditText().setEnabled(true);
                imagem.setVisibility(View.GONE);
                nomeUsuario.setVisibility(View.GONE);
                escolherUser.setVisibility(View.GONE);
            }
        });

        escolherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!id_Usuario.equals("")) {
                    Intent intent = new Intent(pesqAgendaUser.this, escolherServico.class);
                    intent.putExtra("idSalao", idSalao);
                    SharedPreferences.Editor e = getSharedPreferences.edit();
                    e.putString("idUserAgendamento",id_Usuario);
                    e.apply();
                    e.commit();
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Escolha um usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            }
        });
    }

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
        AlertDialog.Builder builder = new AlertDialog.Builder(pesqAgendaUser.this);
        builder.setView(view);
        alerta = builder.create();
        alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alerta.show();
    }
    //-------------------------------------

    //--------- calendario para data de nascimento--------------
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new fragmentUsuario.DatePickerFragment();
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

            ((TextInputLayout) getActivity().findViewById(R.id.txt_nascimento)).getEditText().setText(output);
        }
    }
    //------------------------------------------------------------

    public boolean  verificaTelefone(String telefone){
        boolean verifica = false;
        String num = "";
        String num2 = "";
        num = telefone.replace("(" , " ");
        num = num.replace(")" , " ");
        num2 = num.replaceAll(" ","");
        if(num2.length()>= 10)
            verifica = true;
        else
            verifica = false;

        return  verifica;
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

    public boolean verificaCamposCadastro()
    {
        Boolean status = false;
        String Vemail = cadEmailNovo.getEditText().getText().toString();
        String Vsenha = cadSenhaNova.getEditText().getText().toString();
        String Vnome =nomeUsuario.getEditText().getText().toString();
        String Vtelefone = cadTelefoneUser.getEditText().getText().toString();
        String Vestado = cadEstadoUser.getSelectedItem().toString();
        String Vcidade = cadCidadeUser.getEditText().getText().toString();

        if(!Vnome.equals("") && !Vtelefone.equals("") && !Vestado.equals("") && !Vcidade.equals("") && !Vemail.equals("") && !Vsenha.equals(""))
        {
            if(verificaTelefone(Vtelefone)) {
                status = true;
            }
            else {
                status = false;
                cadTelefoneUser.getEditText().requestFocus();
            }
        }
        else
        {
            if(Vemail.equals(""))
                cadEmailNovo.getEditText().requestFocus();
            else
            if(Vsenha.equals(""))
                cadSenhaNova.getEditText().requestFocus();
            else
            if(Vnome.equals(""))
                nomeUsuario.getEditText().requestFocus();
            else
            if(Vtelefone.equals(""))
                cadTelefoneUser.getEditText().requestFocus();
            else
            if(Vestado.equals(""))
                cadEstadoUser.requestFocus();
            else
            if(Vcidade.equals(""))
                cadCidadeUser.getEditText().requestFocus();
        }
        return  status;
    }


    //-------- função para salvar o usuario-------------
    public void salvarUsuario() {
        if(!verificaCamposCadastro())
        {
            loading.fechar();
            Toast.makeText(pesqAgendaUser.this, "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
        }
        else
        {
            RequestBody email = RequestBody.create(MediaType.parse("text/plain"),  cadEmailNovo.getEditText().getText().toString());
            RequestBody senha = RequestBody.create(MediaType.parse("text/plain"), cadSenhaNova.getEditText().getText().toString());
            RequestBody nome = RequestBody.create(MediaType.parse("text/plain"), nomeUsuario.getEditText().getText().toString());
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

            if (tipoImagem != "" && img64 != "") {
                mine = RequestBody.create(MediaType.parse("multipart/form-data"), tipoImagem);
                converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), img64);
            }

            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<IdNovoUsuario> callSalvaUser = iApi.CadastroLoginUsuario(email,senha,converter64, mine, nome, apelido, sexo, dataNascimento, telefone, cep, endereco, numero, bairro, estado, cidade, obs);
            callSalvaUser.enqueue(new Callback<IdNovoUsuario>() {
                @Override
                public void onResponse(Call<IdNovoUsuario> call, Response<IdNovoUsuario> response) {
                    Log.d("xex",String.valueOf(response.code())+ " -- " +response.message());
                    loading.fechar();
                    qtTentativaRealizadaSalvar = 0;
                    if (response.isSuccessful()) {

                        switch (response.code())
                        {
                            case 200:
                                Toast.makeText(pesqAgendaUser.this, "Usuario Criado !! ", Toast.LENGTH_LONG).show();
                                IdNovoUsuario idNovoUsuario = response.body();
                                Intent intent = new Intent(pesqAgendaUser.this, escolherServico.class);
                                intent.putExtra("idSalao", idSalao);
                                SharedPreferences.Editor e = getSharedPreferences.edit();
                                e.putString("idUserAgendamento",String.valueOf(idNovoUsuario.getIdUsuario()));
                                e.apply();
                                e.commit();
                                startActivity(intent);
                                break;

                            case 400:
                                switch (response.message())
                                {
                                    case "02":
                                        Toast.makeText(pesqAgendaUser.this, "parametros incorretos", Toast.LENGTH_LONG).show();
                                        break;

                                    case "03":
                                        Toast.makeText(pesqAgendaUser.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
                                        break;

                                    case "09":
                                        Toast.makeText(pesqAgendaUser.this, "Este Email já está sendo utilizado!!", Toast.LENGTH_LONG).show();
                                        break;
                                }
                                break;
                        }
                        pegarUsuario(IdLogin);
                    }
                    callSalvaUser.cancel();
                }

                @Override
                public void onFailure(Call<IdNovoUsuario> call, Throwable t) {
                    if (qtTentativaRealizadaSalvar < qtTentativas) {
                        qtTentativaRealizadaSalvar++;
                        salvarUsuario();
                    } else {
                        loading.fechar();
                    }
                }
            });
        }
    }
    //---------------------------------------------------

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
                    ContentResolver cResolver = getContentResolver();
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
                        imagem.setImageBitmap(img.getBitmap());
                    }
                    loading.fechar();
                }
                break;

            case imagem_camera:
                if (resultCode == RESULT_OK)
                {
                    Image img = new Image();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    Uri uri = img.getImageUri(pesqAgendaUser.this, photo);
                    File file = new File(img.getRealPathFromURI(pesqAgendaUser.this,uri));
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
                        imagem.setImageBitmap(img.getBitmap());
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar

                Log.d("xex","home");
                if(!getSharedPreferences.getString("idUserAgendamento","").equals(""))
                {
                    Log.d("xex","tem");
                    IApi iApi = IApi.retrofit.create(IApi.class);
                    final Call<ResponseBody> callDeletaser = iApi.ExcluirUsuarioAnonimo(getSharedPreferences.getString("idUserAgendamento",""));
                    callDeletaser.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d("xex","algo" + String.valueOf(response.code()));
                            if(response.isSuccessful())
                            {
                                Log.d("xex","sucesso");
                                switch (response.code()) {
                                    case 204:
                                        Log.d("xex","apagou");
                                        SharedPreferences.Editor e = getSharedPreferences.edit();
                                        e.remove("idUserAgendamento");
                                        e.apply();
                                        e.commit();
                                        finish();
                                        break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d("xex","falha");
                        }
                    });
                }
                SharedPreferences.Editor e = getSharedPreferences.edit();
                e.remove("idUserAgendamento");
                e.apply();
                e.commit();
                finish();
                break;
            default:break;
        }
        return true;
    }

    //bt voltar
    //para aapgar
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!getSharedPreferences.getString("idUserAgendamento","").equals(""))
        {
            Log.d("xex","tem");
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callDeletaser = iApi.ExcluirUsuarioAnonimo(getSharedPreferences.getString("idUserAgendamento",""));
            callDeletaser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("xex","algo" + String.valueOf(response.code()));
                    if(response.isSuccessful())
                    {
                        Log.d("xex","sucesso");
                        switch (response.code()) {
                            case 204:
                                Log.d("xex","apagou");
                                SharedPreferences.Editor e = getSharedPreferences.edit();
                                e.remove("idUserAgendamento");
                                e.apply();
                                e.commit();
                                finish();
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("xex","falha");
                }
            });
        }
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
                        nomeUsuario.getEditText().setText(user.getNome());

                        if (user.getLinkImagem() != "") {
                            Picasso.with(pesqAgendaUser.this).load(config.getWebService() + user.getLinkImagem()).into(imagem);
                        }
                        else
                        {
                            imagem.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
                        }
                        id_Usuario = String.valueOf(user.getIdUsuario());
                        imagem.setVisibility(View.VISIBLE);
                        nomeUsuario.setVisibility(View.VISIBLE);
                        escolherUser.setVisibility(View.VISIBLE);
                        nomeUsuario.getEditText().setEnabled(false);
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
