package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.BuscaSalao;
import com.stylehair.nerdsolutions.stylehair.classes.GetUsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.Adaptador_funcionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class busca_salao extends AppCompatActivity {

    RecyclerView lista;
    List<BuscaSalao> salaos;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    Loading loading;

    String query;

    ImageButton busca;
    ImageButton filtrar;
    EditText nome;
    TextView kilometroRedor;
    String cidade="";
    double latitude;
    double longitude;
    int kilometro = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_busca);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Buscar");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            query = bundle.getString("query");
        }

        kilometroRedor = (TextView) findViewById(R.id.txtKilometroRedor);
        loading = new Loading(busca_salao.this);

        lista = (RecyclerView) findViewById(R.id.listBuscaSaloes);
        lista.setHasFixedSize(true);

        busca = (ImageButton) findViewById(R.id.bt_encontrar);
        nome = (EditText) findViewById(R.id.txt_query);
        filtrar = (ImageButton) findViewById(R.id.bt_filtrar);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(busca_salao.this,Filtros.class);
                startActivityForResult(intent,1);
            }
        });

        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getBusca(kilometro,nome.getText().toString(),cidade,latitude,longitude);

                loading.abrir("BUSCANCO...");
            }
        });




        minhaPosicao();
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



    public void minhaPosicao()
    {
        latitude = -20.52717426;
        longitude = -47.42805847;
    }

    public void getBusca(final int kilometro, final String nome, final String cidade, final double latitude, final double longitude)
    {

        RequestBody Kilometro = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(kilometro));
        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        RequestBody Cidade = RequestBody.create(MediaType.parse("text/plain"), cidade);
        RequestBody Nome = RequestBody.create(MediaType.parse("text/plain"), nome);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<BuscaSalao>> callBuscaSaloes = iApi.BuscarSalao(Latitude,Longitude,Cidade,Nome,Kilometro);
        callBuscaSaloes.enqueue(new Callback<List<BuscaSalao>>() {
            @Override
            public void onResponse(Call<List<BuscaSalao>> call, Response<List<BuscaSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaSaloes.cancel();

                loading.fechar();

                switch (response.code())
                {
                    case 200:

                        List<BuscaSalao> ListaSalao = response.body();
                        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                        layout.setOrientation(LinearLayoutManager.VERTICAL);
                        lista.setAdapter(new Adaptador_BuscaSalao(ListaSalao));
                        lista.setLayoutManager(layout);
                        lista.setClickable(true);
                        break;

                    case 400:

                        break;
                }



            }

            @Override
            public void onFailure(Call<List<BuscaSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    getBusca(kilometro, nome, cidade, latitude,longitude);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }



    //--------- quando escolhe uma imagem---------------------------------
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

      if(requestCode==1)
      {
          if (resultCode == RESULT_OK) {
              cidade = data.getStringExtra("cidade");
              kilometro =  Integer.valueOf(data.getStringExtra("kilometro"));
              kilometroRedor.setText("Até "+ String.valueOf(kilometro)+"km de você");
          }
      }

    }
    //----------------------------------------------------------------------

}
