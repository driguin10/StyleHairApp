package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.BuscaSalao;
import com.stylehair.nerdsolutions.stylehair.telas.Mapa;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class busca_salao extends AppCompatActivity implements LocationListener {
    RecyclerView lista;
    List<BuscaSalao> salaos;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int qtTentativaRealizadaMais = 0;
    Loading loading;
    private LocationManager locationManager;
    String query = "";
    ImageButton busca;
    ImageButton filtrar;
    EditText nome;
    TextView kilometroRedor;
    String cidade="";
    String cidadeGpg="";
    double latitude;
    double longitude;
    int kilometro = 5;
    Location myLocation;
    String Provider;
    int idLogin;
    private int  totalItemCount, firstVisibleItem, currentitem;
    boolean isScrolling;
    ProgressBar progressoMais;
    LinearLayoutManager layout;
    List<BuscaSalao> ListaSalao;
    int QTRESULT = 15; // quantidade de registros que ira trazer do banco a cada atualização
    int CURRENTRESULT = 0; //  guarda a pagina que ja foi solicitada na atualizacao
    int inicioAUX = 0; // guarda o periodo inicial da atualizacao
    int fimAUX = 0; // guarda o periodo final da atualizacao
    int filtro = 0; // 0= lat_long , 1= cidade

    boolean carrego =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_busca);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Buscar");
        Drawable upArrow = ContextCompat.getDrawable(busca_salao.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(busca_salao.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idLogin = getSharedPreferences.getInt("idLogin", -1);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            query = bundle.getString("query");
        }
        progressoMais = (ProgressBar) findViewById(R.id.progressMais);
        kilometroRedor = (TextView) findViewById(R.id.txtKilometroRedor);
        loading = new Loading(busca_salao.this);
         layout = new LinearLayoutManager(getApplicationContext());
        lista = (RecyclerView) findViewById(R.id.listBuscaSaloes);
        lista.setHasFixedSize(true);

        busca = (ImageButton) findViewById(R.id.bt_encontrar);
        nome = (EditText) findViewById(R.id.txt_query);
        nome.setText(query);
        kilometroRedor.setText("Até " + String.valueOf(kilometro) + "Km de você");
        filtrar = (ImageButton) findViewById(R.id.bt_filtrar);
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(busca_salao.this,Filtros.class);
                intent.putExtra("kilometro",kilometro);
                intent.putExtra("cidade",cidade);
                startActivityForResult(intent,1);
            }
        });
        busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.abrir("Aguarde...");
                minhaPosicao();
                if(filtro==0)//pegando pela localização do aparelho
                    getBusca(kilometro,nome.getText().toString(),"",latitude,longitude,idLogin);
                else
                    getBusca(kilometro,nome.getText().toString(),cidade,0,0,idLogin);
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lista.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = true;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                currentitem = layout.findLastVisibleItemPosition();

               // totalItemCount = layout.getItemCount();
                totalItemCount = lista.getLayoutManager().getItemCount();



                firstVisibleItem = layout.findFirstVisibleItemPosition();

                Log.d("xex",String.valueOf(currentitem) + " - " + String.valueOf(firstVisibleItem) + " - " + String.valueOf(totalItemCount)+ " - " );

                if(!carrego && isScrolling && (currentitem + firstVisibleItem -2 == totalItemCount) )
                {
                    carrego = true;
                    isScrolling = false;
                    Log.d("xex","carrega");
                    carregarMais();
                }

            }
        });

        layout.setOrientation(LinearLayoutManager.VERTICAL);
        lista.setLayoutManager(layout);
        lista.setClickable(true);
        minhaPosicao();
        busca.callOnClick();
    }


    public void carregarMais(){
        int inii = CURRENTRESULT;
        int fim = CURRENTRESULT + QTRESULT;
        if(inii != inicioAUX && fim !=fimAUX)
        {
            inicioAUX = inii;
            fimAUX = fim;
            progressoMais.setVisibility(View.VISIBLE);
            getMais(kilometro, nome.getText().toString(), cidade, latitude, longitude, idLogin, inii, QTRESULT);
        }
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
        Provider = locationManager.getBestProvider(new Criteria(),true);
        try
        {
            myLocation = locationManager.getLastKnownLocation(Provider);
            if(myLocation!=null) {
                latitude = myLocation.getLatitude();
                longitude = myLocation.getLongitude();
                Geocoder geocoder = new Geocoder(busca_salao.this);
                List<Address> enderecos = geocoder.getFromLocation(latitude,longitude,1);

                if(enderecos.get(0).getLocality()!=null)
                    cidadeGpg=enderecos.get(0).getLocality();
                else
                if(enderecos.get(0).getSubAdminArea()!=null)
                    cidadeGpg = enderecos.get(0).getSubAdminArea();

                if(!cidadeGpg.equals(""))
                kilometroRedor.setText("Até " + String.valueOf(kilometro) + "km de você(" + cidadeGpg+")");
            }
            else
            {
                latitude = -20.52717426;
                longitude = -47.42805847;
            }
           // busca.callOnClick();
        }
        catch (SecurityException seg)
        { }
        catch (Exception e)
        { }
    }

    public void getBusca(final int kilometro, final String nome, final String cidade, final double latitude, final double longitude,final int IdLogin)
    {
        RequestBody Kilometro = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(kilometro));
        RequestBody IDLOGIN = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(IdLogin));

        RequestBody Latitude;
        if(latitude != 0)
            Latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        else
             Latitude = RequestBody.create(MediaType.parse("text/plain"), "");

        RequestBody Longitude;
        if(longitude!=0)
            Longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        else
            Longitude = RequestBody.create(MediaType.parse("text/plain"),"");

        RequestBody Cidade;
        if(!cidade.equals(""))
            Cidade = RequestBody.create(MediaType.parse("text/plain"), cidade);
        else
            Cidade = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody Nome = RequestBody.create(MediaType.parse("text/plain"), nome);
        RequestBody Pagina = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));
        RequestBody QtResultados = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(QTRESULT));
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<BuscaSalao>> callBuscaSaloes = iApi.BuscarSalao(Latitude,Longitude,Cidade,Nome,Kilometro,IDLOGIN,Pagina,QtResultados);
        callBuscaSaloes.enqueue(new Callback<List<BuscaSalao>>() {
            @Override
            public void onResponse(Call<List<BuscaSalao>> call, Response<List<BuscaSalao>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaSaloes.cancel();
                loading.fechar();
                switch (response.code())
                {
                    case 200:
                        ListaSalao = response.body();

                        lista.setAdapter(new Adaptador_BuscaSalao(ListaSalao));


                        CURRENTRESULT = CURRENTRESULT + QTRESULT;
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BuscaSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    getBusca(kilometro, nome, cidade, latitude,longitude,IdLogin);
                }
                else {
                    loading.fechar();
                }
            }
        });

    }

    public void getMais(final int kilometro, final String nome, final String cidade, final double latitude, final double longitude,final int IdLogin,final int limIni,final int limFim)
    {
        RequestBody Kilometro = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(kilometro));
        RequestBody IDLOGIN = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(IdLogin));
        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude));
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude));
        RequestBody Cidade = RequestBody.create(MediaType.parse("text/plain"), cidade);
        RequestBody Nome = RequestBody.create(MediaType.parse("text/plain"), nome);
        RequestBody LimiteIni = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(limIni));
        RequestBody QtResultados = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(limFim));
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<BuscaSalao>> callBuscaSaloes = iApi.BuscarSalao(Latitude,Longitude,Cidade,Nome,Kilometro,IDLOGIN,LimiteIni,QtResultados);
        callBuscaSaloes.enqueue(new Callback<List<BuscaSalao>>() {
            @Override
            public void onResponse(Call<List<BuscaSalao>> call, Response<List<BuscaSalao>> response) {
                qtTentativaRealizadaMais = 0 ;
                callBuscaSaloes.cancel();
                progressoMais.setVisibility(View.GONE);
                switch (response.code())
                {
                    case 200:
                        List<BuscaSalao> ListaSalaoMore = response.body();
                        ListaSalao.addAll(ListaSalaoMore);//adiciona a lista os novos registros atualizados
                        lista.getAdapter().notifyDataSetChanged();
                        CURRENTRESULT= CURRENTRESULT+QTRESULT;
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BuscaSalao>> call, Throwable t) {
                if (qtTentativaRealizadaMais < qtTentativas) {
                    qtTentativaRealizadaMais++;
                    getMais(kilometro, nome, cidade, latitude,longitude,IdLogin,limIni,limFim);
                }
                else {
                    progressoMais.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

      if(requestCode==1)
      {
          if (resultCode == RESULT_OK) {
              cidade = data.getStringExtra("cidade");
              kilometro =  Integer.valueOf(data.getStringExtra("kilometro"));
              if(kilometro == 0 && !cidade.equals(""))
              {
                  filtro = 1;
                  kilometroRedor.setText("cidade : " + cidade);
                  getBusca(kilometro,nome.getText().toString(),cidade,0,0,idLogin);
                  loading.abrir("Aguarde...");
              }
              else {
                  filtro = 0;
                  kilometroRedor.setText("Até " + String.valueOf(kilometro) + "km de você(" + cidadeGpg+")");
                  getBusca(kilometro,nome.getText().toString(),cidade,latitude,longitude,idLogin);
                  loading.abrir("Aguarde...");
              }




          }
      }

        if(requestCode==2)
        {
            busca.callOnClick();
        }

    }
    //----------------------------------------------------------------------


    @Override
    public void onResume(){
        super.onResume();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }catch (SecurityException s)
        {

        }
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) { }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}



}
