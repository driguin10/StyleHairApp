package com.stylehair.nerdsolutions.stylehair.telas;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.HorariosAgenda;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.horarios.Adaptador_agenda_horarios;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.horarios.horarios_agenda;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class minha_agenda extends AppCompatActivity implements DatePickerListener{


    RecyclerView lista;
    Loading loading;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    String id;
    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minha_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_minha_agenda);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Meus Agendamentos");
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            tipo = bundle.getString("tipo");
        }
        Drawable upArrow = ContextCompat.getDrawable(minha_agenda.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(minha_agenda.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        if(tipo.equals("0"))
            id = getSharedPreferences.getString("idUsuario", "-1");
        else
            {
               String idFunc = getSharedPreferences.getString("idFuncionario", "-1");
               String idGer = getSharedPreferences.getString("idGerente", "-1");
               if(!idFunc.equals("-1"))
                  id =  idFunc;
               else
               if(!idGer.equals("-1"))
                   id =  idGer;

        }


        Log.d("xex","id-"+id+" tipo-"+tipo);
        loading = new Loading(minha_agenda.this);
        lista = (RecyclerView) findViewById(R.id.listaAgendamentos);
        lista.setHasFixedSize(true);
        HorizontalPicker picker = (HorizontalPicker) findViewById(R.id.agendaH);

        // initialize it and attach a listener
        Resources resources = getBaseContext().getResources();
        picker
                .setListener(this)
                .setDays(20)
                .setOffset(10)
                .setDateSelectedColor(Color.GRAY)// dia selecionado
                .setDateSelectedTextColor(Color.WHITE)//nurmero dia selecionado
                .setMonthAndYearTextColor(Color.LTGRAY)//mes
                .setTodayDateBackgroundColor(Color.GRAY)//fundo dia atual
                .setTodayDateTextColor(Color.RED)//numero do dia atual
                .setUnselectedDayTextColor(Color.GRAY)
                .setDayOfWeekTextColor(Color.WHITE)

                //botao today----------
                .setTodayButtonTextColor(Color.RED)
                .showTodayButton(true)

                //----------------------

                .init();
        picker.setBackgroundColor(Color.DKGRAY);
        TextView btToday = (TextView)findViewById(com.github.jhonnyx2012.horizontalpicker.R.id.tvToday);
        btToday.setText("HOJE");
        picker.setDate(new DateTime());
    }

    @Override
    public void onDateSelected(@NonNull final DateTime dateSelected) {
        SimpleDateFormat formataDataCalendario = new SimpleDateFormat("yyyy-MM-dd");
        Date dataCalend = dateSelected.toDate();
        String dataCalendarioFormatada = formataDataCalendario.format(dataCalend);
        atualizaHorarios(dataCalendarioFormatada,id,tipo);
        loading.abrir("Carregando...");
    }

    public void atualizaHorarios(final String data ,final String id, final String who)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<MeuAgendamento>> callAgendamento = iApi.buscarAgendamento(who,id,data);
        callAgendamento.enqueue(new Callback<List<MeuAgendamento>>() {
            @Override
            public void onResponse(Call<List<MeuAgendamento>> call, Response<List<MeuAgendamento>> response) {

                loading.fechar();
                qtTentativaRealizada = 0;
                if(response.isSuccessful()) {
                    List<MeuAgendamento> meuAgendamento = response.body();
                    LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
                    layout.setOrientation(LinearLayoutManager.VERTICAL);
                    lista.setAdapter(new Adaptador_minhaAgenda(meuAgendamento));
                    lista.setLayoutManager(layout);
                    lista.setClickable(true);
                }

            }

            @Override
            public void onFailure(Call<List<MeuAgendamento>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    atualizaHorarios(data,id,who);
                }
                loading.fechar();
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
