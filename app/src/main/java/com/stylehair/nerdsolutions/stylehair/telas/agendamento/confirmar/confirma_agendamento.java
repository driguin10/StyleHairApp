package com.stylehair.nerdsolutions.stylehair.telas.agendamento.confirmar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.Adaptador_ecolherservico_salao;
import com.stylehair.nerdsolutions.stylehair.telas.login.logar;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class confirma_agendamento extends AppCompatActivity {
    String idUsuario;
    String listaServicos;
    String idFuncionario;
    String idSalao;
    String Data;
    ArrayList<String> ServicosLista;
    String horaIni;
    String horaFim;
    RecyclerView lista;
    TextView txtValorTotal;
    TextView txtData;
    String nomeFuncionario;
    String imagemfuncionario;
    CircleImageView imageFunc;
    TextView nomeFunc;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;
    Button btConfirmar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_agendamento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_confirmar_agenda);
        loading = new Loading(this);
        btConfirmar=(Button) findViewById(R.id.bt_Confirmar);
        txtValorTotal = (TextView) findViewById(R.id.txtValorTotal);
        nomeFunc = (TextView) findViewById(R.id.txtNomeFuncionario);
        txtData = (TextView) findViewById(R.id.txtData);
        imageFunc = (CircleImageView) findViewById(R.id.img_funcionario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Detalhe do Agendamento");
        Drawable upArrow = ContextCompat.getDrawable(confirma_agendamento.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(confirma_agendamento.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            listaServicos = bundle.getString("idServicos");//só ids dos serviços
            idFuncionario = bundle.getString("idFuncionario");
            idSalao = bundle.getString("idSalao");
            ServicosLista = bundle.getStringArrayList("listaServicos");
            horaIni = bundle.getString("horaIni");
            horaFim = bundle.getString("horaFim");
            Data = bundle.getString("data");
            String[] DataAux = Data.split("-");
            Data = DataAux[2]+"/"+DataAux[1]+"/"+DataAux[0];
             nomeFuncionario= bundle.getString("nomeFuncionario");
             imagemfuncionario= bundle.getString("imagemFuncionario");
        }
        SharedPreferences getSharedPreferencesL = PreferenceManager
                .getDefaultSharedPreferences(this);

         idUsuario = getSharedPreferencesL.getString("idUsuario","");

        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        lista = (RecyclerView) findViewById(R.id.ListaServicosAgenda);
        lista.setHasFixedSize(true);
        lista.setAdapter(new Adaptador_servicoConfirmAgenda(ServicosLista,txtValorTotal));
        lista.setLayoutManager(layout);

        String horarioAux = "Dia "+Data+"\n"+ "ás " +horaIni;
        txtData.setText(horarioAux);

        Picasso.with(this).load("http://stylehair.xyz/" + imagemfuncionario).into(imageFunc);
        nomeFunc.setText(nomeFuncionario);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loading.abrir("Aguarde...");
                salvarAgendamento();
            }
        });
    }


    public void salvarAgendamento()
    {
        RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"), idSalao);
        RequestBody IDFUNCIONARIO = RequestBody.create(MediaType.parse("text/plain"), idFuncionario);
        RequestBody IDSERVICO = RequestBody.create(MediaType.parse("text/plain"),listaServicos);
        RequestBody IDUSUARIO = RequestBody.create(MediaType.parse("text/plain"),idUsuario);

        String[] DataAux = Data.split("/");
        String DataPServer = DataAux[2]+"-"+DataAux[1]+"-"+DataAux[0];


        RequestBody DATA = RequestBody.create(MediaType.parse("text/plain"), DataPServer);
        RequestBody HORAINI = RequestBody.create(MediaType.parse("text/plain"), horaIni);
        RequestBody HORAFIM = RequestBody.create(MediaType.parse("text/plain"), horaFim);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callSalvaAgenda = iApi.AgendaSalvar(IDSALAO,IDFUNCIONARIO,IDSERVICO,IDUSUARIO,DATA,HORAINI,HORAFIM);
        callSalvaAgenda.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                qtTentativaRealizada = 0 ;
                callSalvaAgenda.cancel();

                loading.fechar();

                switch (response.code())
                {
                    case 204:
                        Toast.makeText(confirma_agendamento.this,"Agendado com sucesso",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(confirma_agendamento.this, principal.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    salvarAgendamento();
                }
                else {
                    loading.fechar();
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
