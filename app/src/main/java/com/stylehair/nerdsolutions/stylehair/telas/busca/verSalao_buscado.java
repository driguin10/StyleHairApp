package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.classes.AtualizaInfos;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.VerSalao;
import com.stylehair.nerdsolutions.stylehair.classes.idNovoFavorito;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_avaliacao.Adaptador_avaliacoes_comentario;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario.fragment_funcionarios_salao;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos.fragment_servicos_do_salao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verSalao_buscado extends AppCompatActivity {
Loading loading ;
Config config;
    int qtTentativas = 3;
    int qtTentativaRealizadaSalvar = 0;
    int qtTentativaRealizadaComentario = 0;
    int qtTentativaRealizadaSalvComentario = 0;
    int qtTentativaRealizadaExcFav = 0;
    int qtTentativaRealizadaSavFav = 0;
    String idSalao;
    String idFavorito;

    AlertDialog alerta;

    TextView nomeSalao;

    CircleImageView imagemSalao;
    Button avaliacao;
    ImageButton btFavorito;
    RecyclerView listaComentario;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;

    Salao salao;
    SectionsPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_salao_buscado);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idSalao =bundle.getString("idSalao");
            idFavorito =bundle.getString("idFavorito");
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_verSalaoBuscado);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        Drawable upArrow = ContextCompat.getDrawable(verSalao_buscado.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(verSalao_buscado.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle("");
        myToolbar.bringToFront();
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container_salaoBuscado);
        tabLayout = (TabLayout) findViewById(R.id.tabs_salaoBuscado);


        avaliacao = (Button) findViewById(R.id.btAvaliar);
        btFavorito = (ImageButton) findViewById(R.id.btFavorito);

        avaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = getLayoutInflater();
                final View view = li.inflate(R.layout.activity_avaliar, null);
                listaComentario = (RecyclerView) view.findViewById(R.id.listaComentarios);
                listaComentario.setHasFixedSize(true);
                loading.abrir("Aguarde...");
                getAvaliacoes(idSalao);

                view.findViewById(R.id.btSair).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        alerta.dismiss();
                    }
                });

                view.findViewById(R.id.btAvaliar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RatingBar estrelas = (RatingBar) view.findViewById(R.id.estrelasAvalia);
                        EditText comentario = (EditText)view.findViewById(R.id.txtComentario);

                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String Data = dateFormat.format(date);
                        if(estrelas.getRating()>0 || !comentario.getText().toString().equals("") ) {
                            loading.abrir("Aguarde...");
                            SalvarAvaliacao(idSalao, String.valueOf(estrelas.getRating()), comentario.getText().toString(), Data);
                        }
                        else
                        {
                            Toast.makeText(verSalao_buscado.this,"Dê alguma avaliação !!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(verSalao_buscado.this);
                builder.setView(view);
                alerta = builder.create();
                alerta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alerta.setCanceledOnTouchOutside(false);
                alerta.show();
            }
        });





        loading = new Loading(this);
        config = new Config();

        nomeSalao = (TextView) findViewById(R.id.txt_nomeSalao_buscado);
        imagemSalao = (CircleImageView) findViewById(R.id.imagemSalao_buscado);

        loading.abrir("Aguarde...");

        if(idFavorito.equals("-1"))
        {
            btFavorito.setBackgroundResource(R.drawable.icone_favorito_of);
        }
        else
        {
            btFavorito.setBackgroundResource(R.drawable.icone_favorito);
        }
        btFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!idFavorito.equals("-1"))
                {
                    excluiFavorito(idFavorito);
                }
                else
                {
                    SharedPreferences getSharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(verSalao_buscado.this);
                    String idLogin = String.valueOf(getSharedPreferences.getInt("idLogin", -1));

                    salvaFavorito(idSalao,idLogin);

                }
            }
        });
        pegarSalao(idSalao);
    }


    public void getAvaliacoes(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<AvaliacaoSalao>> callBuscaAvaliacoes = iApi.BuscarAvaliacoes(id);
        callBuscaAvaliacoes.enqueue(new Callback<List<AvaliacaoSalao>>() {
            @Override
            public void onResponse(Call<List<AvaliacaoSalao>> call, Response<List<AvaliacaoSalao>> response) {
                qtTentativaRealizadaComentario = 0 ;
                callBuscaAvaliacoes.cancel();
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        List<AvaliacaoSalao> ListaAvaliacoes = response.body();

                        //não mostra comentario vazio
                        for (int x=0;x<ListaAvaliacoes.size();x++)
                        {
                            if(ListaAvaliacoes.get(x).getComentario().equals(""))
                            {
                                ListaAvaliacoes.remove(x);
                            }
                        }



                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        listaComentario.setAdapter(new Adaptador_avaliacoes_comentario(ListaAvaliacoes));
                        listaComentario.setLayoutManager(layout);
                        listaComentario.setClickable(true);
                        break;

                    case 400:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<AvaliacaoSalao>> call, Throwable t) {
                if (qtTentativaRealizadaComentario < qtTentativas) {
                    qtTentativaRealizadaComentario++;
                    getAvaliacoes(idSalao);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }

    private void setupViewPager(ViewPager viewPager,Salao salao) {
        adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Bundle bundleOSalao = new Bundle();
        bundleOSalao.putString("idSalao",String.valueOf(salao.getIdSalao()));
        String endereco = salao.getEndereco()+", "+String.valueOf(salao.getNumero())+", "+salao.getBairro()+", "+salao.getCidade()+", "+salao.getComplemento();
        bundleOSalao.putString("endereco",endereco);
        bundleOSalao.putString("email",salao.getEmail());
        bundleOSalao.putString("sobre",salao.getSobre());
        bundleOSalao.putString("status",String.valueOf(salao.getStatus()));
        bundleOSalao.putString("agendamento",String.valueOf(salao.getAgendamento()));
        bundleOSalao.putString("cnpj",salao.getCnpj());
        String telefones = salao.getTelefone1() + " , " + salao.getTelefone2();
        bundleOSalao.putString("telefones",telefones);
        bundleOSalao.putString("segE",salao.getSegE());
        bundleOSalao.putString("segS",salao.getSegS());
        bundleOSalao.putString("terE",salao.getTerE());
        bundleOSalao.putString("terS",salao.getTerS());
        bundleOSalao.putString("quaE",salao.getQuaE());
        bundleOSalao.putString("quaS",salao.getQuaS());
        bundleOSalao.putString("quiE",salao.getQuiE());
        bundleOSalao.putString("quiS",salao.getQuiS());
        bundleOSalao.putString("sexE",salao.getSexE());
        bundleOSalao.putString("sexS",salao.getSexS());
        bundleOSalao.putString("sabE",salao.getSabE());
        bundleOSalao.putString("sabS",salao.getSabS());
        bundleOSalao.putString("domE",salao.getDomE());
        bundleOSalao.putString("domS",salao.getDomS());



        Fragment Osalao = new fragment_o_salao();
        Osalao.setArguments(bundleOSalao);

        adapter.addFragment(Osalao, "");


        Bundle Bservico = new Bundle();
        Bservico.putString("idServico", String.valueOf(salao.getIdSalao()));
        Fragment Fservicos = new fragment_servicos_do_salao();
        Fservicos.setArguments(Bservico);
        adapter.addFragment(Fservicos, "");

        Bundle Bfuncionarios = new Bundle();
        Bfuncionarios.putString("idServico", String.valueOf(salao.getIdSalao()));
        Fragment Ffuncionarios = new fragment_funcionarios_salao();
        Ffuncionarios.setArguments(Bfuncionarios);
        adapter.addFragment(Ffuncionarios, "");



        viewPager.setAdapter(adapter);
        configuraTab();
    }

    private void configuraTab()
    {
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icone_home_branco));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icone_servicos_branco));
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.icone_funcionario_branco));
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


    public void pegarSalao(final String idSalao){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<VerSalao> callVisualizaSalao = iApi.verSalaoBusca(idSalao);
        callVisualizaSalao.enqueue(new Callback<VerSalao>() {
            @Override
            public void onResponse(Call<VerSalao> call, Response<VerSalao> response) {
                loading.fechar();
                callVisualizaSalao.cancel();
                switch (response.code()) {
                    case 200:
                        VerSalao ver_salao = response.body();

                        List<Salao> listaSalao = ver_salao.getSalao();
                        salao = new Salao();
                        salao = listaSalao.get(0);

                        List<Usuario> listaGerente = ver_salao.getGerente();
                        Usuario gerente = new Usuario();
                        gerente = listaGerente.get(0);


                        nomeSalao.setText(salao.getNome());
                        if (salao.getLinkImagem() != "")
                            Picasso.with(verSalao_buscado.this).load(config.getWebService() + salao.getLinkImagem()).centerCrop().resize(250, 250).into(imagemSalao);




                        setupViewPager(mViewPager,salao);
                        break;
                }
            }

            @Override
            public void onFailure(Call<VerSalao> call, Throwable t) {
                if (qtTentativaRealizadaSalvar < qtTentativas) {
                    qtTentativaRealizadaSalvar++;

                    pegarSalao(idSalao);
                }
                else {
                    loading.fechar();

                }

            }
        });

    }
    //-------------------------------------------------------



    public void SalvarAvaliacao(final String idSalao, final String pontos, final String comentario, final String data)
    {
        RequestBody IDsalao = RequestBody.create(MediaType.parse("text/plain"),idSalao);
        RequestBody Pontos = RequestBody.create(MediaType.parse("text/plain"),pontos);
        RequestBody Comentario = RequestBody.create(MediaType.parse("text/plain"),comentario);
        RequestBody Data = RequestBody.create(MediaType.parse("text/plain"),data);


            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callSalvarAvaliacao = iApi.SalvarAvaliacao(IDsalao,Pontos,Comentario,Data);
            callSalvarAvaliacao.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    callSalvarAvaliacao.cancel();
                    loading.fechar();
                    qtTentativaRealizadaSalvComentario = 0;
                    switch (response.code())
                    {
                        case 204:
                            Toast.makeText(verSalao_buscado.this,"Obrigado por Avaliar",Toast.LENGTH_LONG).show();
                            alerta.dismiss();
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(verSalao_buscado.this,"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "04":
                                    Toast.makeText(verSalao_buscado.this,"Erro!!",Toast.LENGTH_LONG).show();
                                    break;

                            }
                            break;
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                    if (qtTentativaRealizadaSalvComentario < qtTentativas) {
                        qtTentativaRealizadaSalvComentario++;
                        SalvarAvaliacao( idSalao, pontos,  comentario, data);
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

    public void excluiFavorito( final String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiFavorito = iApi.DeletarFavorito(id);
        callExcluiFavorito.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                qtTentativaRealizadaExcFav = 0;
                switch (response.code())
                {
                    case 204:
                        idFavorito = "-1";
                        atualizaCoracao(false);
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.removeTopico(salao.getTopicoNotificacao());
                        break;

                    case 400:
                        Toast.makeText(verSalao_buscado.this,"Houve um erro!!",Toast.LENGTH_LONG).show();
                        break;
                }

                callExcluiFavorito.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaExcFav < qtTentativas) {
                    qtTentativaRealizadaExcFav++;
                    excluiFavorito(id);
                } else {

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

    public void salvaFavorito( final String idsalao,final String idlogin)
    {
        RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"), idsalao);
        RequestBody IDLOGIN = RequestBody.create(MediaType.parse("text/plain"), idlogin);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<idNovoFavorito> callSalvarFavorito = iApi.SalvarFavorito(IDSALAO,IDLOGIN);
        callSalvarFavorito.enqueue(new Callback<idNovoFavorito>() {
            @Override
            public void onResponse(Call<idNovoFavorito> call, Response<idNovoFavorito> response) {

                qtTentativaRealizadaSavFav = 0;
                switch (response.code())
                {
                    case 200:
                        idNovoFavorito IdNovoFavorito = new idNovoFavorito();
                        IdNovoFavorito = response.body();
                        idFavorito = String.valueOf(IdNovoFavorito.getIdFavorito());
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.addTopico(salao.getTopicoNotificacao());
                        atualizaCoracao(true);
                        break;

                    case 400:
                        Toast.makeText(verSalao_buscado.this,"Houve um erro!!",Toast.LENGTH_LONG).show();
                        break;
                }

                callSalvarFavorito.cancel();
            }

            @Override
            public void onFailure(Call<idNovoFavorito> call, Throwable t) {
                if (qtTentativaRealizadaSavFav < qtTentativas) {
                    qtTentativaRealizadaSavFav++;
                    salvaFavorito(idsalao,idlogin);
                }
            }
        });
    }

    public void  atualizaCoracao(boolean favorito)
    {
        if(favorito)
        {
            btFavorito.setBackgroundResource(R.drawable.icone_favorito);
        }
        else{
            btFavorito.setBackgroundResource(R.drawable.icone_favorito_of);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent intent) {
        if (requestCode == 0) {
            if (ResultCode == RESULT_OK) {
                    if(intent.getData().toString().equals("userCad")) {

                        AtualizaInfos atualizaInfos = new AtualizaInfos(verSalao_buscado.this);
                        atualizaInfos.atualizatipo(false);

                     Intent intent2 = new Intent(this,verSalao_buscado.class);
                      intent2.putExtra("idSalao",idSalao);
                        intent2.putExtra("idFavorito",idFavorito);
                        startActivity(intent2);
                        finish();
                    }
            }
        }
    }
    }


