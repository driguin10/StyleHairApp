package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.ArrayList;
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
    Button btProsseguir;
    ImageButton btListaServicos;
    TextView qtServicosEscolhido;
    ArrayList<String> listFinal;
    LinearLayoutManager layout;
    List<ServicoSalao> ListaServicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_servico);
        loading = new Loading(escolherServico.this);
        layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        Bundle bundle = getIntent().getExtras();
        listFinal = new ArrayList<>();
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
        qtServicosEscolhido = (TextView) findViewById(R.id.txtQuantidadeServico);
        btProsseguir = (Button) findViewById(R.id.btProsseguir) ;
        btListaServicos = (ImageButton) findViewById(R.id.btVerListaServicos);
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
                qtTentativaRealizada = 0 ;
                callBuscaServico.cancel();
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        ListaServicos = response.body();
                        lista.setAdapter(new Adaptador_ecolherservico_salao(ListaServicos,qtServicosEscolhido,listFinal,btProsseguir,btListaServicos));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
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

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent intent){
        if(requestCode == 1){
            Bundle bundle = intent.getExtras();
            listFinal = bundle.getStringArrayList("lista");
            qtServicosEscolhido.setText(String.valueOf(listFinal.size()));
            lista.setAdapter(new Adaptador_ecolherservico_salao(ListaServicos,qtServicosEscolhido,listFinal,btProsseguir,btListaServicos));
            lista.setLayoutManager(layout);
            lista.setClickable(true);
        }
    }
}
