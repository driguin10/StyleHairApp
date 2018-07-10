package com.stylehair.nerdsolutions.stylehair.telas.minhaConta;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.APICepService;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.CaixaDialogo;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Image;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Mask;
import com.stylehair.nerdsolutions.stylehair.auxiliar.UfToName;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEP;
import com.stylehair.nerdsolutions.stylehair.telas.login.logar;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.io.ByteArrayOutputStream;
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

import static android.app.Activity.RESULT_OK;


public class fragmentUsuario extends Fragment {

    private OnFragmentInteractionListener mListener;

    AlertDialog alerta;
    ProgressDialog dialog;
    static final int imagem_interna = 1;
    static final int imagem_camera = 0;

    int qtTentativas = 3;
    int qtTentativaRealizadaLoad = 0;
    int qtTentativaRealizadaSalvar = 0;
    int qtTentativaRealizadaEditar = 0;


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

    Spinner cadEstadoUser;
    Spinner cadSexoUser;

    Button BtSalvar;
    Button BtCarregaImagem ;

    ImageButton BtPesqData;
    ImageButton BtExcluirImagem;

    int IdLogin;

    String filepath; // caminho da imagem
    String img64 =""; // base64 da imagem
    String tipoImagem=""; // extensao da imagem
    int percentImgArq = 20; //compressao da imagem vinda do arquivo interno
    int percentImgCam = 99; //compressao da imagem vinda do arquivo interno

    Drawable bitmapPadrao;//guarda a imagem padrao do usuario
    Config config;

    Boolean okUsuario = false;
    String LinkImagem = "";
    String ImageAntiga = "";
    View view;
    VerificaConexao verificaConexao;



    SharedPreferences getSharedPreferences;
    Loading loading;
boolean statusCad =false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_usuario, container, false);
       // caixaDialogo = new CaixaDialogo();
        //caixaDialogo.MenssagemDialog(getActivity(),"Aguarde...Carregando!!!");
        config = new Config();
        verificaConexao = new VerificaConexao();
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        IdLogin = getSharedPreferences.getInt("idLogin", -1);
        loading = new Loading(getActivity());
        loading.abrir("Aguarde...");


       view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Intent data = new Intent();
                    if(statusCad)
                        data.setData(Uri.parse("userCad"));
                    else
                        data.setData(Uri.parse(""));
                    ((Activity)getActivity()).setResult(RESULT_OK, data);
                    getActivity().finish();
                    return true;

                }
                return false;
            }
        } );


//--------------- Casting dos componentes --------------------------

        cadNomeUser = (TextInputLayout) view.findViewById(R.id.txt_userNome);
        cadApelidoUser = (TextInputLayout) view.findViewById(R.id.txt_usrApelido);
        cadTelefoneUser = (TextInputLayout) view.findViewById(R.id.txt_usrTelefone);
        cadEnderecoUser = (TextInputLayout) view.findViewById(R.id.txt_usrEndereco);
        cadBairroUser = (TextInputLayout)view.findViewById(R.id.txt_usrBairro);
        cadNumeroUser = (TextInputLayout)view.findViewById(R.id.txt_usrNumero);
        cadEstadoUser = (Spinner) view.findViewById(R.id.Sp_usrEstado);
        cadCidadeUser = (TextInputLayout) view.findViewById(R.id.txt_usrCidade);
        cadObsUser = (TextInputLayout) view.findViewById(R.id.txt_usrObs);
        cadCepUser = (TextInputLayout) view.findViewById(R.id.txt_usrCep);
        cadNascimento = (TextInputLayout) view.findViewById(R.id.txt_nascimento);
        cadSexoUser = (Spinner) view.findViewById(R.id.spn_usrSexo);
        BtExcluirImagem = (ImageButton)view.findViewById(R.id.bt_excluiImg);
        BtPesqData = (ImageButton) view.findViewById(R.id.pesquisa_data);
        BtCarregaImagem = (Button) view.findViewById(R.id.bt_caregaImg);
        BtSalvar = (Button)view.findViewById(R.id.bt_salvarUser);
        ImagemUser = (CircleImageView) view.findViewById(R.id.imagemUser);
        //--------------------------------------------------------------------

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
        //-------guarda a imagem padrao ---------
        bitmapPadrao = ImagemUser.getDrawable();

        //----------- abre dialog para escolher imagem-------------------
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

        //--------- botão salvar usuario--------------------------------
        BtSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaConexao.verifica(getActivity()))
                {
                    //caixaDialogo.MenssagemDialog(getActivity(),"Aguarde...Enviando dados !!!");
                    loading.abrir("Aguarde...");
                    if(okUsuario) { // se na hora de buscar usuario tiver seta com true
                        editarUsuario();
                    }
                    else {
                        salvarUsuario();
                    }
                }
            }
        });

        //-------- faz a busca dos dados do usuario no servidor-------

        if(verificaConexao.verifica(getContext())) {
             pegarUsuario(IdLogin);
        }
        else {
            //caixaDialogo.fecharCaixa();
            loading.fechar();
            Toast.makeText(getActivity(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
           Logout logout = new Logout();
           logout.deslogar(getActivity(),false);
        }

        return view;
    }

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

    public boolean verificaCampos()
    {
        Boolean status = false;
        String Vnome =cadNomeUser.getEditText().getText().toString();
        String Vtelefone = cadTelefoneUser.getEditText().getText().toString();
        String Vestado = cadEstadoUser.getSelectedItem().toString();
        String Vcidade = cadCidadeUser.getEditText().getText().toString();


        if(!Vnome.equals("") && !Vtelefone.equals("") && !Vestado.equals("") && !Vcidade.equals(""))
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
        }
        return  status;
    }


    //-------- função para salvar o usuario-------------
    public void salvarUsuario() {
        if(!verificaCampos())
        {
            loading.fechar();
            Toast.makeText(getContext(), "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
        }
        else
        {
            RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(IdLogin));
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

            if (tipoImagem != "" && img64 != "") {
                mine = RequestBody.create(MediaType.parse("multipart/form-data"), tipoImagem);
                converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), img64);
            }

            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callSalvaUser = iApi.salvarUsuario(converter64, mine, iduser, nome, apelido, sexo, dataNascimento, telefone, cep, endereco, numero, bairro, estado, cidade, obs);

            callSalvaUser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.fechar();
                    qtTentativaRealizadaSalvar = 0;
                    if (response.isSuccessful()) {

                        switch (response.code())
                        {
                            case 204:
                                pegarUsuario(IdLogin);
                                Toast.makeText(getContext(), "Salvo com sucesso", Toast.LENGTH_LONG).show();

                                ((minhaConta)getActivity()).setStatus(true);
                                break;

                            case 400:
                                switch (response.message())
                                {
                                    case "02":
                                        Toast.makeText(getContext(), "parametros incorretos", Toast.LENGTH_LONG).show();
                                        break;

                                    case "03":
                                        Toast.makeText(getContext(), "Erro ao salvar", Toast.LENGTH_LONG).show();
                                        break;
                                }
                                break;
                        }
                        pegarUsuario(IdLogin);
                    }
                    callSalvaUser.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizadaSalvar < qtTentativas) {
                        qtTentativaRealizadaSalvar++;
                        salvarUsuario();
                    } else {
                        loading.fechar();

                        if (t instanceof IOException) {
                            Log.d("xex", "this is an actual network failure timeout:( inform the user and possibly retry");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else if (t instanceof IllegalStateException) {
                            Log.d("xex", "ConversionError");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else {
                            Log.d("xex", "erro");
                            Log.d("xex", String.valueOf(t.getCause()));
                            Log.d("xex", String.valueOf(t.getLocalizedMessage()));
                        }

                    }
                }
            });
        }
    }
    //---------------------------------------------------


    //-------- função para salvar o usuario-------------
    public void editarUsuario()
    {
        if(!verificaCampos())
        {
            loading.fechar();
            Toast.makeText(getContext(), "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
        }
        else
        {
            RequestBody idlogin = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(IdLogin));
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
            RequestBody mine = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody converter64 = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody imagemAntiga = RequestBody.create(MediaType.parse("text/plain"), ImageAntiga);


            if (tipoImagem != "" && img64 != "") {
                mine = RequestBody.create(MediaType.parse("text/plain"), tipoImagem);
                converter64 = RequestBody.create(MediaType.parse("text/plain"), img64);
            }
            else {
                converter64 = RequestBody.create(MediaType.parse("text/plain"), LinkImagem);
                Log.d("xex","sem imagem");
            }

            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callEditarUser = iApi.EditarUsuario(converter64, mine, idlogin, nome, apelido, sexo, dataNascimento, telefone, cep, endereco, numero, bairro, estado, cidade, obs,imagemAntiga);
            callEditarUser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.fechar();
                    if (response.isSuccessful()) {
                        qtTentativaRealizadaEditar = 0;

                        switch (response.code())
                        {
                            case 204:
                                Toast.makeText(getContext(), "Editado com sucesso", Toast.LENGTH_LONG).show();
                                break;

                            case 400:
                                switch (response.message())
                                {
                                    case "02":
                                        Toast.makeText(getContext(), "parametros incorretos", Toast.LENGTH_LONG).show();
                                        break;

                                    case "04":
                                        Toast.makeText(getContext(), "Erro ao editar", Toast.LENGTH_LONG).show();
                                        break;
                                }
                                break;
                        }

                    }
                    pegarUsuario(IdLogin);
                    callEditarUser.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                    if (qtTentativaRealizadaEditar < qtTentativas) {
                        qtTentativaRealizadaEditar++;
                        editarUsuario();
                    } else {
                        loading.fechar();

                        if (t instanceof IOException) {
                            Log.d("xex", "this is an actual network failure timeout:( inform the user and possibly retry");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else if (t instanceof IllegalStateException) {
                            Log.d("xex", "ConversionError");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else {
                            Log.d("xex", "erro");
                            Log.d("xex", String.valueOf(t.getCause()));
                            Log.d("xex", String.valueOf(t.getLocalizedMessage()));
                        }

                    }
                }
            });
        }
    }
    //---------------------------------------------------


    //---- função para pegar dados do usuario do servidor----
    public void pegarUsuario(final int idLogin){
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Usuario>> callBuscaUser = iApi.BuscaUsuario(idLogin);
        callBuscaUser.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                loading.fechar();
                callBuscaUser.cancel();
                switch (response.code()) {
                    case 200:

                        BtSalvar.setText("Salvar alteração");

                        List<Usuario> users = response.body();
                        Usuario user = users.get(0);
                        cadNomeUser.getEditText().setText(user.getNome());
                        cadApelidoUser.getEditText().setText(user.getApelido());
                        cadTelefoneUser.getEditText().setText(user.getTelefone());
                        cadEnderecoUser.getEditText().setText(user.getEndereco());
                        cadBairroUser.getEditText().setText(user.getBairro());
                        cadNumeroUser.getEditText().setText(String.valueOf(user.getNumero()));
                        cadCidadeUser.getEditText().setText(user.getCidade());
                        cadObsUser.getEditText().setText(user.getObs());
                        cadCepUser.getEditText().setText(user.getCep());

                        okUsuario = true;
                        LinkImagem = user.getLinkImagem();
                        ImageAntiga = user.getLinkImagem();
                        qtTentativaRealizadaLoad = 0;

                        for(int i= 0; i < cadEstadoUser.getAdapter().getCount(); i++)
                        {
                            if(cadEstadoUser.getAdapter().getItem(i).toString().contains(user.getEstado()))
                            {
                                cadEstadoUser.setSelection(i);
                            }
                        }

                        for(int i= 0; i < cadSexoUser.getAdapter().getCount(); i++)
                        {
                            if(cadSexoUser.getAdapter().getItem(i).toString().contains(user.getSexo()))
                            {
                                cadSexoUser.setSelection(i);
                            }
                        }

                        if (user.getLinkImagem() != "") {
                            Picasso.with(getContext()).load(config.getWebService() + user.getLinkImagem()).into(ImagemUser);
                        }
                        else
                        {
                            ImagemUser.setImageDrawable(getResources().getDrawable(R.drawable.img_padrao_user));
                        }

                        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dataFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                        String outputNascimento = "";
                        try {
                            outputNascimento = dataFormat2.format(dataFormat.parse(user.getDataNascimento()));
                        } catch (ParseException p) {
                        }

                        cadNascimento.getEditText().setText(outputNascimento);


                        SharedPreferences.Editor e = getSharedPreferences.edit();
                        e.putInt("idUsuario",user.getIdUsuario());


                        e.putString("typeUserApp","COMUMUSER");
                        e.putString("nomeUser",user.getNome());
                        e.putString("linkImagem",user.getLinkImagem());

                        e.apply();

                        break;


                    case 400:
                        if (response.message().equals("1")) {
                            BtSalvar.setText("Salvar");
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
                    pegarUsuario(idLogin);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        alerta = builder.create();
        alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alerta.show();

    }
    //-------------------------------------

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
                    ContentResolver cResolver = getContext().getContentResolver();
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

                    Uri uri = img.getImageUri(getActivity(), photo);
                    File file = new File(img.getRealPathFromURI(getActivity(),uri));
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
        newFragment.show(getFragmentManager(), "datePicker");
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


    // do fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onFragmentInteraction(Uri uri);
    }
}
