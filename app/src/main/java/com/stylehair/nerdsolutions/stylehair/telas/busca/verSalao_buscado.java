package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.Config;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.FuncionarioVerSalao;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.VerSalao;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verSalao_buscado extends AppCompatActivity {
Loading loading ;
Config config;
    int qtTentativas = 3;
    int qtTentativaRealizadaSalvar = 0;
    String idSalao;



    TextView nomeSalao;

    CircleImageView imagemSalao;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_salao_buscado);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idSalao =bundle.getString("idSalao");
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

        loading = new Loading(this);
        config = new Config();

        nomeSalao = (TextView) findViewById(R.id.txt_nomeSalao_buscado);
        imagemSalao = (CircleImageView) findViewById(R.id.imagemSalao_buscado);

        loading.abrir("aguarde");
        pegarSalao(idSalao);
    }


    private void setupViewPager(ViewPager viewPager,Salao salao) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        Bundle bundleOSalao = new Bundle();

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
                        Salao salao = new Salao();
                        salao = listaSalao.get(0);

                        List<Usuario> listaGerente = ver_salao.getGerente();
                        Usuario gerente = new Usuario();
                        gerente = listaGerente.get(0);



                        List<FuncionarioVerSalao> funcionarios = ver_salao.getFuncionarios();
                        FuncionarioVerSalao funcionarioVerSalao = new FuncionarioVerSalao();

                        List<ServicoSalao> servicos = ver_salao.getServicos();

                        Log.d("xex",String.valueOf(servicos.get(0).getServico()));

                            nomeSalao.setText(salao.getNome());
                        if (salao.getLinkImagem() != "") {
                            Picasso.with(verSalao_buscado.this).load(config.getWebService() + salao.getLinkImagem()).centerCrop().resize(250, 250).into(imagemSalao);
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
                Log.d("xex",t.getMessage());
                Log.d("xex",t.getCause().toString());
            }
        });

    }
    //-------------------------------------------------------

}
