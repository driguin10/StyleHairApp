package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.Adaptador_servico_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.servicos_salao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class escolherServico extends AppCompatActivity {

    RecyclerView lista;
    List<ServicoSalao> servicosSalao;
    String idSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_servico);
        loading = new Loading(escolherServico.this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            idSalao = bundle.getString("idSalao");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_escolherServico);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Escolha os Serviços");
        Drawable upArrow = ContextCompat.getDrawable(escolherServico.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(escolherServico.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        lista = (RecyclerView) findViewById(R.id.listaServicosSalao);
        lista.setHasFixedSize(true);
        loading.abrir("atualizando");
        getServicos(idSalao);
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


    public void getServicos(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<ServicoSalao>> callBuscaServico = iApi.BuscaServicosSalao(id);
        callBuscaServico.enqueue(new Callback<List<ServicoSalao>>() {
            @Override
            public void onResponse(Call<List<ServicoSalao>> call, Response<List<ServicoSalao>> response) {
                Log.d("xex",String.valueOf(response.code()) + response.message());
                qtTentativaRealizada = 0 ;
                callBuscaServico.cancel();

                loading.fechar();

                switch (response.code())
                {
                    case 200:
                        List<ServicoSalao> ListaServicos = response.body();
                        Log.d("xex","lii - " +  ListaServicos.get(0).getServico());
                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_ecolherservico_salao(ListaServicos));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ServicoSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    getServicos(idSalao);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }
}
