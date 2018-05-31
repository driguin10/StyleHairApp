package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.HorariosAgenda;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.Adaptador_servico_funcionario_escolhido;
import com.stylehair.nerdsolutions.stylehair.telas.minha_agenda;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class horarios_agenda extends AppCompatActivity implements DatePickerListener {

    String listaServicos;
    String idFuncionario;
    String idSalao;
    String data;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    RecyclerView lista;
    Loading loading;
    ArrayList<String> ServicosLista;
    LocalTime tempoServico = LocalTime.parse("00:00:00");
    TextView txtHoraEscolha;
    Button Prosseguir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios_agenda);
        Bundle bundle = getIntent().getExtras();
        loading = new Loading(horarios_agenda.this);
        txtHoraEscolha=(TextView) findViewById(R.id.txtHoraEscolhida);
        Prosseguir = (Button) findViewById(R.id.btProsseguir);
        Prosseguir.setAlpha(.4f);
        Prosseguir.setEnabled(false);
        if(bundle !=null)
        {
            listaServicos = bundle.getString("servicos");//só ids dos serviços
            idFuncionario = bundle.getString("idFuncionario");
            idSalao = bundle.getString("idSalao");
            ServicosLista = bundle.getStringArrayList("ListaServicos");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_horarios_agenda);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Horarios");
        Drawable upArrow = ContextCompat.getDrawable(horarios_agenda.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(horarios_agenda.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        lista = (RecyclerView) findViewById(R.id.listaHorarios);
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
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formataDataCalendario = new SimpleDateFormat("yyyy-MM-dd");
        Date dataAtual = new Date();
        Date dataCalend = dateSelected.toDate();
        //dataCalend.setTime(dataAtual.getTime());
        String dataCalendarioFormatada = formataDataCalendario.format(dataCalend);
        String dataAtualSting= formataData.format(dataAtual);
        String dataCalendarioString = formataData.format(dataCalend);


        if(dataCalend.after(dataAtual))
        {
            atualizaHorarios(dataCalendarioFormatada);
            loading.abrir("Carregando...");
        }
        else
        {
            if(!dataAtualSting.equals(dataCalendarioString)) {
                ArrayList<String> horarios = new ArrayList<>();
                lista.setAdapter(new Adaptador_agenda_horarios(horarios, lista,"","",txtHoraEscolha,Prosseguir,listaServicos,idFuncionario,idSalao));
                Toast.makeText(horarios_agenda.this,"Esta data não possui Horarios !!",Toast.LENGTH_LONG).show();
            }
            else
            {
                atualizaHorarios(dataCalendarioFormatada);
                loading.abrir("Carregando...");
            }
        }



    }


    public void atualizaHorarios(final String data)
    {
        RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"), idSalao);
        RequestBody IDFUNCIONARIO = RequestBody.create(MediaType.parse("text/plain"), idFuncionario);
        RequestBody SERVICOS = RequestBody.create(MediaType.parse("text/plain"), listaServicos);
        RequestBody DATA = RequestBody.create(MediaType.parse("text/plain"), data);
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<HorariosAgenda> callHorarios = iApi.HorariosAgenda(IDSALAO, IDFUNCIONARIO, SERVICOS, DATA);

        callHorarios.enqueue(new Callback<HorariosAgenda>() {
            @Override
            public void onResponse(Call<HorariosAgenda> call, Response<HorariosAgenda> response) {

                loading.fechar();
                qtTentativaRealizada = 0;
                HorariosAgenda horariosAgenda = new HorariosAgenda();
                horariosAgenda = response.body();
                ArrayList<String>horarios = horariosAgenda.getHorarios();
                String tempo = horariosAgenda.getTempoServico();
                String intervalo = horariosAgenda.getIntervalo();
                lista.setAdapter(new Adaptador_agenda_horarios(horarios,lista,tempo,intervalo,txtHoraEscolha,Prosseguir,listaServicos,idFuncionario,idSalao));
                lista.setLayoutManager(new GridLayoutManager(horarios_agenda.this,2));
                lista.setClickable(true);
                if(horarios !=null)
                    if(horarios.size()==0)
                    {
                        Toast.makeText(horarios_agenda.this,"Esta data não possui Horarios !!",Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<HorariosAgenda> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    atualizaHorarios(data);
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
