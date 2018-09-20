package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.AtualizaInfos;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.VerSalao;
import com.stylehair.nerdsolutions.stylehair.classes.idNovoFavorito;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolherServico;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_avaliacao.Adaptador_avaliacoes_comentario;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_funcionario.fragment_funcionarios_salao;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos.fragment_servicos_do_salao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.minhaConta;

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
    SharedPreferences getSharedPreferences;
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
    ImageView imgFundo;
    Button avaliacao;
    ImageButton btFavorito;
    RecyclerView listaComentario;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    Salao salao;
    SectionsPageAdapter adapter;
    FloatingActionButton btAgendar;
    String tipo;
    CardView cardStatus;
    TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_salao_buscado);
        Bundle bundle = getIntent().getExtras();
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(verSalao_buscado.this);

        tipo = getSharedPreferences.getString("typeUserApp","COMUM");
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
        btAgendar = (FloatingActionButton) findViewById(R.id.floatAgendar);
        mViewPager = (ViewPager) findViewById(R.id.container_salaoBuscado);
        tabLayout = (TabLayout) findViewById(R.id.tabs_salaoBuscado);
        avaliacao = (Button) findViewById(R.id.btAvaliar);
        btFavorito = (ImageButton) findViewById(R.id.btFavorito);
        imgFundo = (ImageView) findViewById(R.id.img_fundo_salao);
        loading = new Loading(this);
        config = new Config();
        nomeSalao = (TextView) findViewById(R.id.txt_nomeSalao_buscado);
        imagemSalao = (CircleImageView) findViewById(R.id.imagemSalao_buscado);

        txtStatus = (TextView) findViewById(R.id.txtStatus);
        cardStatus = (CardView) findViewById(R.id.cardStatus);

        btAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tipo.equals("COMUM")) {
                    Intent intent = new Intent(verSalao_buscado.this, escolherServico.class);
                    intent.putExtra("idSalao", idSalao);
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(verSalao_buscado.this)
                            .setTitle("Deseja concluir seu cadastro?")
                            .setMessage("Para efetuar agendamentos é necessario concluir seu cadastro.")
                            .setIcon(R.drawable.icone_funcionario_preto)
                            .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(verSalao_buscado.this, minhaConta.class);
                                    startActivityForResult(intent, 0);
                                }
                            })
                            .setNegativeButton("não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });


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
        loading.abrir("Aguarde...");
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
       tabLayout.getTabAt(1).select();
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
                        if (salao.getLinkImagem() != "") {

                            Picasso.with(verSalao_buscado.this).load(config.getWebService() + salao.getLinkImagem()).centerCrop().resize(250, 250).into(imagemSalao);

                        }

                        if(salao.getStatus() == 1)
                        {
                            imagemSalao.setBorderColor(getResources().getColor(R.color.corAberto));
                            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.corAberto));
                            txtStatus.setText("ABERTO");
                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.corAberto));
                        }else
                        if(salao.getStatus() == 0)
                        {
                            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.corFechado));
                            imagemSalao.setBorderColor(getResources().getColor(R.color.corFechado));
                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.corFechado));
                            txtStatus.setText("FECHADO");
                        }else
                        {
                            cardStatus.setCardBackgroundColor(getResources().getColor(R.color.corAlmoco));
                            imagemSalao.setBorderColor(getResources().getColor(R.color.corAlmoco));
                            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.corAlmoco));
                            txtStatus.setText("ALMOÇO");
                        }


                        if(salao.getAgendamento() == 1)
                            btAgendar.setVisibility(View.VISIBLE);

                        if(tipo.equals("COMUM"))
                        {
                            btAgendar.setAlpha(1f);
                            btAgendar.setClickable(false);
                        }

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
                        /*
                        pegar lista do shared jogar em uma lista procurar qual topico que é igual e remover e depois salvar novamente
                         */


                        String topicos = getSharedPreferences.getString("topicosFavoritos","");
                        ArrayList<String> arrayTopFavoritos = new ArrayList<>();
                        Gson gson = new Gson();
                        if(!topicos.equals(""))
                        {
                            arrayTopFavoritos = gson.fromJson(topicos, new TypeToken<ArrayList<String>>(){}.getType());

                            for(int x=0;x<arrayTopFavoritos.size();x++){
                                if(arrayTopFavoritos.get(x).equals(salao.getTopicoNotificacao())){
                                    arrayTopFavoritos.remove(x);
                                }
                            }
                            String jsonTopicoFavoritos = gson.toJson(arrayTopFavoritos);
                            SharedPreferences.Editor e = getSharedPreferences.edit();
                            e.putString("topicosFavoritos",jsonTopicoFavoritos);
                            e.apply();
                            e.commit();

                        }
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



                        String topicos = getSharedPreferences.getString("topicosFavoritos","");
                        ArrayList<String> arrayTopFavoritos = new ArrayList<>();
                        Gson gson = new Gson();
                        if(!topicos.equals(""))
                        {
                            Log.d("xex", "tem coisa");
                            arrayTopFavoritos = gson.fromJson(topicos, new TypeToken<ArrayList<String>>(){}.getType());
                            if(arrayTopFavoritos.size()>0)
                            {
                                Log.d("xex", "é maior q zero");
                                for(int x=0;x<arrayTopFavoritos.size();x++)
                                {
                                    topicoNotificacao.removeTopico(arrayTopFavoritos.get(x));
                                    Log.d("xex", "remove topic - "+ arrayTopFavoritos.get(x));
                                }
                            }
                            arrayTopFavoritos.add(salao.getTopicoNotificacao());
                        }
                        String jsonTopicoFavoritos = gson.toJson(arrayTopFavoritos);
                        SharedPreferences.Editor e = getSharedPreferences.edit();
                        e.putString("topicosFavoritos",jsonTopicoFavoritos);
                        e.apply();
                        e.commit();


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


