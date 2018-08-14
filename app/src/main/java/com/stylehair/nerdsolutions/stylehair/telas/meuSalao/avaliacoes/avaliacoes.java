package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class avaliacoes extends AppCompatActivity{
    RecyclerView lista;
    List<AvaliacaoSalao> ListaAvaliacao;
    AvaliacaoSalao avaliacaoSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;
    String idSalao;

    TextView pontos;
    TextView qtComentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacoes);
        loading = new Loading(avaliacoes.this);
        pontos = (TextView) findViewById(R.id.txtqt_pontos);
        qtComentarios = (TextView) findViewById(R.id.txtqt_comentarios);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_avaliacoes);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Avaliações");
        Drawable upArrow = ContextCompat.getDrawable(avaliacoes.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(avaliacoes.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idSalao = getSharedPreferences.getString("idSalao", "");
        lista = (RecyclerView) findViewById(R.id.listComentarios);
        lista.setHasFixedSize(true);
        loading.abrir("Aguarde...");
        getAvaliacoes(idSalao);

    }



    public void getAvaliacoes(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<AvaliacaoSalao>> callBuscaAvaliacoes = iApi.BuscarAvaliacoes(id);
        callBuscaAvaliacoes.enqueue(new Callback<List<AvaliacaoSalao>>() {
            @Override
            public void onResponse(Call<List<AvaliacaoSalao>> call, Response<List<AvaliacaoSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaAvaliacoes.cancel();
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        List<AvaliacaoSalao> ListaAvaliacoes = response.body();

                        for (int x=0;x<ListaAvaliacoes.size();x++)
                        {
                            if(ListaAvaliacoes.get(x).getComentario().equals(""))
                            {
                                ListaAvaliacoes.get(x).setComentario("...............");
                            }
                        }

                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_avaliacoes(ListaAvaliacoes,pontos,qtComentarios,lista));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<AvaliacaoSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
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

}
