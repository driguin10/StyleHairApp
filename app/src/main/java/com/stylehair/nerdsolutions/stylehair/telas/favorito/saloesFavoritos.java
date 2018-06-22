package com.stylehair.nerdsolutions.stylehair.telas.favorito;

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

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.Adaptador_avaliacoes;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.avaliacoes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class saloesFavoritos extends AppCompatActivity {
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;
    RecyclerView lista;
    String idLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloes_favoritos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_saloes_favoritos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Favoritos");
        Drawable upArrow = ContextCompat.getDrawable(saloesFavoritos.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(saloesFavoritos.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idLogin = String.valueOf(getSharedPreferences.getInt("idLogin", -1));
        loading = new Loading(saloesFavoritos.this);
        lista = (RecyclerView) findViewById(R.id.listaFavorito);
        lista.setHasFixedSize(true);
        loading.abrir("Aguarde...");
        getFavoritos(idLogin);
    }


    public void getFavoritos(String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<favorito_usuario>> callBuscaFavorito = iApi.BuscaFavoritoUsuario(id);
        callBuscaFavorito.enqueue(new Callback<List<favorito_usuario>>() {
            @Override
            public void onResponse(Call<List<favorito_usuario>> call, Response<List<favorito_usuario>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaFavorito.cancel();
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        List<favorito_usuario> ListaFavorito = response.body();

                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_favorito(ListaFavorito,lista));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<favorito_usuario>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    getFavoritos(idLogin);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
