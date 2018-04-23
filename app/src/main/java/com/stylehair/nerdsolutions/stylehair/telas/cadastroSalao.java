package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.APICepService;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Image;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Mask;
import com.stylehair.nerdsolutions.stylehair.auxiliar.UfToName;
import com.stylehair.nerdsolutions.stylehair.classes.cep.CEP;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.editar_salao;

import java.io.File;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cadastroSalao extends AppCompatActivity {

    AlertDialog alerta;
    static final int imagem_interna = 1;
    static final int imagem_camera = 0;
    static final int mapa = 2;
    int qtTentativas = 3;
    int qtTentativaRealizadaSalvar = 0;
    CircleImageView ImagemSalao;
    TextInputLayout NomeSalao;
    TextInputLayout Telefone1Salao;
    TextInputLayout Telefone2Salao;
    TextInputLayout CepSalao;
    TextInputLayout EnderecoSalao;
    TextInputLayout BairroSalao;
    TextInputLayout NumeroSalao;
    TextInputLayout CidadeSalao;
    TextInputLayout SobreSalao;
    TextInputLayout EmailSalao;
    TextInputLayout CnpjSalao;
    TextInputLayout ComplementoSalao;
    Spinner EstadoSalao;
    Button SalvarSalao;
    Button CaregaImgSalao ;
    ImageButton ExcluiImgSalao;
    String IdUsuario;
    String filepath; // caminho da imagem
    String img64 =""; // base64 da imagem
    String tipoImagem=""; // extensao da imagem
    int percentImgArq = 20; //compressao da imagem vinda do arquivo interno
    int percentImgCam = 99; //compressao da imagem vinda do arquivo interno
    Image image;
    String ImageAntiga="";
    Drawable bitmapPadrao;//guarda a imagem padrao do usuario
    Config config;
    String LinkImagem = "";
    Loading loading;
    Switch agendar;
    String latitude;
    String longitude;
    Button pegarPosicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cadsalao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Cadastrar Salão");
        loading = new Loading(this);
        config = new Config();
        image = new Image();

        //-------pega o id do login para fazer a consulta---------------
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        IdUsuario = getSharedPreferences.getString("idUsuario", "");
        //---------------------------------------------------------------

        //--------------- Casting dos componentes --------------------------

        NomeSalao = (TextInputLayout) findViewById(R.id.txt_NomeSalao);
        Telefone1Salao = (TextInputLayout) findViewById(R.id.txt_Telefone1Salao);
        Telefone2Salao = (TextInputLayout) findViewById(R.id.txt_Telefone2Salao);
        CepSalao = (TextInputLayout) findViewById(R.id.txt_CepSalao);
        EnderecoSalao = (TextInputLayout) findViewById(R.id.txt_EnderecoSalao);
        BairroSalao = (TextInputLayout) findViewById(R.id.txt_BairroSalao);
        NumeroSalao = (TextInputLayout) findViewById(R.id.txt_NumeroSalao);
        CidadeSalao = (TextInputLayout) findViewById(R.id.txt_CidadeSalao);
        SobreSalao = (TextInputLayout) findViewById(R.id.txt_SobreSalao);
        EmailSalao = (TextInputLayout) findViewById(R.id.txt_EmailSalao);
        CnpjSalao = (TextInputLayout) findViewById(R.id.txt_CnpjSalao);
        ComplementoSalao = (TextInputLayout) findViewById(R.id.txt_ComplementoSalao);
        EstadoSalao = (Spinner) findViewById(R.id.Sp_EstadoSalao);
        ExcluiImgSalao = (ImageButton)findViewById(R.id.bt_excluiImgSalao);
        CaregaImgSalao = (Button) findViewById(R.id.bt_caregaImgSalao);
        SalvarSalao = (Button)findViewById(R.id.bt_salvarSalao);
        ImagemSalao = (CircleImageView) findViewById(R.id.imagemSalao);
        agendar = (Switch)findViewById(R.id.sw_cad_agenda);
        //--------------------------------------------------------------------
        pegarPosicao =(Button) findViewById(R.id.bt_pegarLocalizacao);

        //---------------- adiciona as mascaras no Telefone-Cep-Data --------------------------------------------------
        Telefone1Salao.getEditText().addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, Telefone1Salao.getEditText()));
        Telefone2Salao.getEditText().addTextChangedListener(Mask.insert(Mask.CELULAR_MASK, Telefone2Salao.getEditText()));
        CepSalao.getEditText().addTextChangedListener(Mask.insert(Mask.CEP_MASK, CepSalao.getEditText()));

        //---------------------------------------------------------------------------------------------------------------

        CepSalao.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus) {
                    String cep = CepSalao.getEditText().getText().toString();
                    String novoCep = cep.trim();
                    novoCep = cep.replace("-", "");

                    if(novoCep.length() == 8) {
                        loading.abrir("Buscando endereço...");
                        pegarCep(novoCep);
                    }
                }
            }
        });
        //-------guarda a imagem padrao ---------
        bitmapPadrao = ImagemSalao.getDrawable();

        //----------- abre dialog para escolher imagem-------------------
        CaregaImgSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EscolhaImagem();
            }
        });



        //----- exclui a imagem selecionada do usuario-------------------
        ExcluiImgSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagemSalao.setImageDrawable(bitmapPadrao);
                img64 = "";
                tipoImagem="";
                LinkImagem = "";
            }
        });


        //--------- botão salvar usuario--------------------------------
        SalvarSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Aguarde...Enviando dados !!!");
                    salvarSalao();

            }
        });

        pegarPosicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = EnderecoSalao.getEditText().getText().toString();
                String numero = NumeroSalao.getEditText().getText().toString();
                String Bairro = BairroSalao.getEditText().getText().toString();
                String cidade = CidadeSalao.getEditText().getText().toString();
                String estado = EstadoSalao.getSelectedItem().toString();
                String nome = NomeSalao.getEditText().getText().toString();

                String saida = endereco + "," + numero + "," + Bairro + "," + cidade + "," + estado;
                Intent intent = new Intent(cadastroSalao.this, Mapa.class);
                intent .putExtra("nome",nome);
                intent.putExtra("endereco",saida);
                intent.putExtra("latitude",0);
                intent.putExtra("longitude",0);
                startActivityForResult(intent,mapa);

            }
        });
        //------
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean verificaCampos()
    {
        Boolean status = false;
        String VnomeSalao =NomeSalao.getEditText().getText().toString();
        String VTelefone1Salao = Telefone1Salao.getEditText().getText().toString();
        String VTelefone2Salao = Telefone2Salao.getEditText().getText().toString();
        String VCepSalao = CepSalao.getEditText().getText().toString();
        String VEnderecoSalao = EnderecoSalao.getEditText().getText().toString();
        String VBairroSalao = BairroSalao.getEditText().getText().toString();
        String VNumeroSalao = NumeroSalao.getEditText().getText().toString();
        String VCidadeSalao = CidadeSalao.getEditText().getText().toString();
        String VEmailSalao = EmailSalao.getEditText().getText().toString();
        String VEstadoSalao = EstadoSalao.getSelectedItem().toString();


        if(!VnomeSalao.equals("") && !VTelefone1Salao.equals("") && !VEnderecoSalao.equals("") && !VCepSalao.equals("") && !VBairroSalao.equals("")
                && !VNumeroSalao.equals("")&& !VCidadeSalao.equals("")&& !VEmailSalao.equals("")&& !VEstadoSalao.equals("")&& verificaTelefone(VTelefone2Salao))
        {
            if(verificaTelefone(VTelefone1Salao))
                status = true;
            else {
                status = false;
                Telefone1Salao.requestFocus();
            }
        }
        else
        {
            if(VnomeSalao.equals(""))
                NomeSalao.getEditText().requestFocus();
            else
            if(VTelefone1Salao.equals(""))
                Telefone1Salao.getEditText().requestFocus();
            else
            if(VEnderecoSalao.equals(""))
                EnderecoSalao.requestFocus();
            else
            if(VBairroSalao.equals(""))
                BairroSalao.getEditText().requestFocus();
            else
            if(VCepSalao.equals(""))
                CepSalao.getEditText().requestFocus();
            else
            if(VNumeroSalao.equals(""))
                NumeroSalao.getEditText().requestFocus();
            else
            if(VEstadoSalao.equals(""))
                EstadoSalao.requestFocus();
            else
            if(VCidadeSalao.equals(""))
                CidadeSalao.getEditText().requestFocus();
            else
            if(VEmailSalao.equals(""))
                EmailSalao.getEditText().requestFocus();
            else
            if(!verificaTelefone(VTelefone1Salao))
                Telefone1Salao.requestFocus();
            else
            if(!verificaTelefone(VTelefone2Salao))
                Telefone2Salao.requestFocus();

        }
        return  status;
    }


    public boolean  verificaTelefone(String telefone){
        boolean verifica = false;
        String num = "";
        String num2 = "";
        num = telefone.replace("(" , " ");
        num = num.replace(")" , " ");
        num2 = num.replaceAll(" ","");

        if(num2.length()>= 8 )
            verifica = true;
        else
            verifica = false;
        return  verifica;

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
                    EnderecoSalao.getEditText().setText(cep.getLogradouro());
                    BairroSalao.getEditText().setText(cep.getBairro());
                    CidadeSalao.getEditText().setText(cep.getLocalidade());

                    if(cep.getUf()!=null) {
                        UfToName ufToName = new UfToName();
                        for (int i = 0; i < EstadoSalao.getAdapter().getCount(); i++) {
                            if (EstadoSalao.getAdapter().getItem(i).toString().contains(ufToName.estado(cep.getUf()))) {
                                EstadoSalao.setSelection(i);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }
    //-------------------------------------

    //--------- quando escolhe uma imagem---------------------------------
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case imagem_interna:
                if (resultCode == RESULT_OK) {
                    alerta.dismiss();
                    loading.abrir("Carregando Imagem...");
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filepath = cursor.getString(columnIndex);
                    cursor.close();

                    File f64 = new File(filepath);
                    if(f64!=null)
                    {
                        image.setResizedBitmap(f64,percentImgArq);
                        image.setMimeFromImgPath(f64.getPath());
                    }
                    img64  = image.getBitmapBase64();
                    tipoImagem = image.getMime();

                    if(image.getBitmap()!=null)
                    {
                        ImagemSalao.setImageBitmap(image.getBitmap());
                    }
                    loading.fechar();
                }
                break;

            case imagem_camera:
                if (resultCode == RESULT_OK)
                {
                    alerta.dismiss();
                    loading.abrir("Carregando Imagem...");
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    Uri uri = image.getImageUri(this, photo);

                    File file = new File(image.getRealPathFromURI(this,uri));
                    String pathImgCamera = file.getPath();
                    File f64 = new File(pathImgCamera);
                    if(f64!=null)
                    {
                        image.setResizedBitmap(f64,percentImgCam);
                        image.setMimeFromImgPath(f64.getPath());
                    }
                    img64  = image.getBitmapBase64();
                    tipoImagem = image.getMime();

                    if(image.getBitmap()!=null)
                    {
                        ImagemSalao.setImageBitmap(image.getBitmap());
                    }
                    loading.fechar();
                }
                break;

            case mapa:
                if (resultCode == RESULT_OK) {
                    latitude = data.getStringExtra("latitude");
                    longitude = data.getStringExtra("longitude");
                }
                break;
        }//fim switch

    }
    //----------------------------------------------------------------------

    //-------- função para salvar o usuario-------------
    public void salvarSalao() {

        if(!verificaCampos())
        {
            loading.fechar();
            Toast.makeText(this, "Preencha os campos necessarios !!", Toast.LENGTH_LONG).show();
        }
        else
        {
            RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"), IdUsuario);
            RequestBody nome = RequestBody.create(MediaType.parse("text/plain"), NomeSalao.getEditText().getText().toString());
            RequestBody telefone1Salao = RequestBody.create(MediaType.parse("text/plain"), Telefone1Salao.getEditText().getText().toString());
            RequestBody telefone2Salao = RequestBody.create(MediaType.parse("text/plain"), Telefone2Salao.getEditText().getText().toString());
            RequestBody cepSalao = RequestBody.create(MediaType.parse("text/plain"), CepSalao.getEditText().getText().toString());
            RequestBody enderecoSalao = RequestBody.create(MediaType.parse("text/plain"), EnderecoSalao.getEditText().getText().toString());
            RequestBody bairroSalao = RequestBody.create(MediaType.parse("text/plain"), BairroSalao.getEditText().getText().toString());
            RequestBody numeroSalao = RequestBody.create(MediaType.parse("text/plain"), NumeroSalao.getEditText().getText().toString());
            RequestBody cidadeSalao = RequestBody.create(MediaType.parse("text/plain"), CidadeSalao.getEditText().getText().toString());
            RequestBody estadoSalao = RequestBody.create(MediaType.parse("text/plain"), EstadoSalao.getSelectedItem().toString());
            RequestBody sobreSalao = RequestBody.create(MediaType.parse("text/plain"), SobreSalao.getEditText().getText().toString());
            RequestBody emailSalao = RequestBody.create(MediaType.parse("text/plain"), EmailSalao.getEditText().getText().toString());
            RequestBody cnpjSalao = RequestBody.create(MediaType.parse("text/plain"), CnpjSalao.getEditText().getText().toString());
            RequestBody complementoSalao = RequestBody.create(MediaType.parse("text/plain"), ComplementoSalao.getEditText().getText().toString());
            RequestBody mine = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            RequestBody converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            RequestBody agendamento = RequestBody.create(MediaType.parse("text/plain"), "0");
            RequestBody lati = RequestBody.create(MediaType.parse("text/plain"), latitude);
            RequestBody longi = RequestBody.create(MediaType.parse("text/plain"), longitude);

           if(agendar.isChecked())
           {
               agendamento = RequestBody.create(MediaType.parse("text/plain"), "1");
           }


            if (tipoImagem != "" && img64 != "") {
                mine = RequestBody.create(MediaType.parse("multipart/form-data"), tipoImagem);
                converter64 = RequestBody.create(MediaType.parse("multipart/form-data"), img64);
            }

            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callSalvaSalao = iApi.SalvarSalao(converter64, mine, iduser, nome, telefone1Salao, telefone2Salao, enderecoSalao, bairroSalao, cepSalao, numeroSalao, estadoSalao,lati,longi, cidadeSalao, emailSalao, sobreSalao,cnpjSalao, agendamento,complementoSalao);

            callSalvaSalao.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    loading.fechar();
                    qtTentativaRealizadaSalvar = 0;

                    switch (response.code())
                    {
                        case 204:
                            Toast.makeText(cadastroSalao.this,"Salão salvo , Você agora é um gerente!!",Toast.LENGTH_LONG).show();
                            Logout logout = new Logout();
                            logout.deslogar(cadastroSalao.this,false);
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(cadastroSalao.this,"erro ao salvar!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "03":
                                    Toast.makeText(cadastroSalao.this,"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;
                    }




                    callSalvaSalao.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizadaSalvar < qtTentativas) {
                        qtTentativaRealizadaSalvar++;
                        salvarSalao();
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




}
