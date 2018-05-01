package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.FuncionarioVerSalao;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.VerSalao;
import com.stylehair.nerdsolutions.stylehair.fragment_o_salao;
import com.stylehair.nerdsolutions.stylehair.fragment_servicos_do_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.editar_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.fragment_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.fragment_horarios_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.fragment_servicos_funcionarios;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.SectionsPageAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class verSalao_buscado extends AppCompatActivity {
Loading loading ;
    int qtTentativas = 3;
    int qtTentativaRealizadaSalvar = 0;
    String idSalao;

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_salao_buscado);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_verSalaoBuscado);


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        getSupportActionBar().setTitle("");


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container_salaoBuscado);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_salaoBuscado);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.icone_funcionario));
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.icone_servicos));



        loading = new Loading(this);
        loading.abrir("aguarde");


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idSalao =bundle.getString("idSalao");
        }


        pegarSalao(idSalao);
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_o_salao(), "O salão");
        adapter.addFragment(new fragment_servicos_do_salao(), "Serviços");
       // adapter.addFragment(new fragment_servicos_funcionarios(), "");
        viewPager.setAdapter(adapter);
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


                        Log.d("xex",salao.getNome());
                        Log.d("xex",gerente.getNome());
                        Log.d("xex",funcionarios.get(0).getNome());



                        Log.d("xex",String.valueOf(servicos.get(0).getServico()));





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
