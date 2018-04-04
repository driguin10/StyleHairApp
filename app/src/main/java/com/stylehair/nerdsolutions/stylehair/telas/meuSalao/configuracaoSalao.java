package com.stylehair.nerdsolutions.stylehair.telas.meuSalao;


import android.app.DialogFragment;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;
import com.stylehair.nerdsolutions.stylehair.classes.ConfiguracaoSalao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class configuracaoSalao extends AppCompatActivity {

    ImageButton horaSegE;
    ImageButton horaSegS;
    TextInputLayout txtSegE;
    TextInputLayout txtSegS;
    ToggleButton btStatusSegunda;

    ImageButton horaTerE;
    ImageButton horaTerS;
    TextInputLayout txtTerE;
    TextInputLayout txtTerS;
    ToggleButton btStatusTerca;

    ImageButton horaQuaE;
    ImageButton horaQuaS;
    TextInputLayout txtQuaE;
    TextInputLayout txtQuaS;
    ToggleButton btStatusQuarta;

    ImageButton horaQuiE;
    ImageButton horaQuiS;
    TextInputLayout txtQuiE;
    TextInputLayout txtQuiS;
    ToggleButton btStatusQuinta;

    ImageButton horaSexE;
    ImageButton horaSexS;
    TextInputLayout txtSexE;
    TextInputLayout txtSexS;
    ToggleButton btStatusSexta;

    ImageButton horaSabE;
    ImageButton horaSabS;
    TextInputLayout txtSabE;
    TextInputLayout txtSabS;
    ToggleButton btStatusSabado;

    ImageButton horaDomE;
    ImageButton horaDomS;
    TextInputLayout txtDomE;
    TextInputLayout txtDomS;
    ToggleButton btStatusDomingo;

    Spinner intervaloAgenda;
    Spinner tempoMinimo;

    Loading loading;
    String idSalao = "34";

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_config_salao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configuração do Salão");

        loading = new Loading(configuracaoSalao.this);

        txtSegE = (TextInputLayout) findViewById(R.id.txt_hora_segE);
        txtSegS = (TextInputLayout) findViewById(R.id.txt_hora_segS);
        btStatusSegunda = (ToggleButton) findViewById(R.id.bt_folga_seg);
        horaSegE = (ImageButton) findViewById(R.id.pesquisa_hora_segE);
        horaSegS = (ImageButton) findViewById(R.id.pesquisa_hora_segS);

        txtTerE = (TextInputLayout) findViewById(R.id.txt_hora_terE);
        txtTerS = (TextInputLayout) findViewById(R.id.txt_hora_terS);
        btStatusTerca = (ToggleButton) findViewById(R.id.bt_folga_ter);
        horaTerE = (ImageButton) findViewById(R.id.pesquisa_hora_terE);
        horaTerS = (ImageButton) findViewById(R.id.pesquisa_hora_terS);

        txtQuaE = (TextInputLayout) findViewById(R.id.txt_hora_quaE);
        txtQuaS = (TextInputLayout) findViewById(R.id.txt_hora_quaS);
        btStatusQuarta = (ToggleButton) findViewById(R.id.bt_folga_qua);
        horaQuaE = (ImageButton) findViewById(R.id.pesquisa_hora_quaE);
        horaQuaS = (ImageButton) findViewById(R.id.pesquisa_hora_quaS);

        txtQuiE = (TextInputLayout) findViewById(R.id.txt_hora_quiE);
        txtQuiS = (TextInputLayout) findViewById(R.id.txt_hora_quiS);
        btStatusQuinta = (ToggleButton) findViewById(R.id.bt_folga_qui);
        horaQuiE = (ImageButton) findViewById(R.id.pesquisa_hora_quiE);
        horaQuiS = (ImageButton) findViewById(R.id.pesquisa_hora_quiS);

        txtSexE = (TextInputLayout) findViewById(R.id.txt_hora_sexE);
        txtSexS = (TextInputLayout) findViewById(R.id.txt_hora_sexS);
        btStatusSexta = (ToggleButton) findViewById(R.id.bt_folga_sex);
        horaSexE = (ImageButton) findViewById(R.id.pesquisa_hora_sexE);
        horaSexS = (ImageButton) findViewById(R.id.pesquisa_hora_sexS);

        txtSabE = (TextInputLayout) findViewById(R.id.txt_hora_sabE);
        txtSabS = (TextInputLayout) findViewById(R.id.txt_hora_sabS);
        btStatusSabado = (ToggleButton) findViewById(R.id.bt_folga_sab);
        horaSabE = (ImageButton) findViewById(R.id.pesquisa_hora_sabE);
        horaSabS = (ImageButton) findViewById(R.id.pesquisa_hora_sabS);

        txtDomE = (TextInputLayout) findViewById(R.id.txt_hora_domE);
        txtDomS = (TextInputLayout) findViewById(R.id.txt_hora_domS);
        btStatusDomingo = (ToggleButton) findViewById(R.id.bt_folga_dom);
        horaDomE = (ImageButton) findViewById(R.id.pesquisa_hora_domE);
        horaDomS = (ImageButton) findViewById(R.id.pesquisa_hora_domS);


        intervaloAgenda = (Spinner) findViewById(R.id.spn_intervalo_agenda);
        tempoMinimo = (Spinner) findViewById(R.id.spn_min_agenda);

        txtSegE.setEnabled(false);
        txtSegS.setEnabled(false);

        txtTerE.setEnabled(false);
        txtTerS.setEnabled(false);

        txtQuaE.setEnabled(false);
        txtQuaS.setEnabled(false);

        txtQuiE.setEnabled(false);
        txtQuiS.setEnabled(false);

        txtSexE.setEnabled(false);
        txtSexS.setEnabled(false);

        txtSabE.setEnabled(false);
        txtSabS.setEnabled(false);

        txtDomE.setEnabled(false);
        txtDomS.setEnabled(false);

        btStatusSegunda.setChecked(true);
        btStatusTerca.setChecked(true);
        btStatusQuarta.setChecked(true);
        btStatusQuinta.setChecked(true);
        btStatusSexta.setChecked(true);
        btStatusSabado.setChecked(true);
        btStatusDomingo.setChecked(true);

        muda(true,txtSegE,txtSegS,horaSegE,horaSegS,btStatusSegunda);
        muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
        muda(true,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
        muda(true,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
        muda(true,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
        muda(true,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
        muda(true,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);

        horaSegE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_segE);
            }
        });
        horaSegS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_segS);
            }
        });

        horaTerE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_terE);
            }
        });
        horaTerS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_terS);
            }
        });

        horaQuaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quaE);
            }
        });
        horaQuaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quaS);
            }
        });

        horaQuiE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quiE);
            }
        });
        horaQuiS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quiS);
            }
        });

        horaSexE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sexE);
            }
        });
        horaSexS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sexS);
            }
        });

        horaSabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sabE);
            }
        });
        horaSabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sabS);
            }
        });

        horaDomE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_domE);
            }
        });
        horaDomS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_domS);
            }
        });


        btStatusSegunda.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtSegE,txtSegS,horaSegE,horaSegS,btStatusSegunda);
                else
                    muda(false,txtSegE,txtSegS,horaSegE,horaSegS,btStatusSegunda);
            }
        });

        btStatusTerca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                else
                    muda(false,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
            }
        });


        btStatusQuarta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                else
                    muda(false,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
            }
        });


        btStatusQuinta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                else
                    muda(false,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
            }
        });

        btStatusSexta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                else
                    muda(false,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
            }
        });

        btStatusSabado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                else
                    muda(false,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
            }
        });

        btStatusDomingo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    muda(true,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                else
                    muda(false,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
            }
        });

        loading.abrir("Carregando dados.... aguarde !!");
        pegarConfiguracao(idSalao);
    }



    public void pegarConfiguracao(final String id){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<ConfiguracaoSalao>> callBuscaConfiguracao = iApi.getConfiguracaoSalao(Integer.valueOf(id));
        callBuscaConfiguracao.enqueue(new Callback<List<ConfiguracaoSalao>>() {
            @Override
            public void onResponse(Call<List<ConfiguracaoSalao>> call, Response<List<ConfiguracaoSalao>> response) {
                loading.fechar();
                Log.d("xex",String.valueOf(response.code()));
                callBuscaConfiguracao.cancel();
                switch (response.code()) {
                    case 200:

                        List<ConfiguracaoSalao> configuracoes = response.body();
                        ConfiguracaoSalao configSalao = configuracoes.get(0);


                        if(configSalao.getSegE()!=null && configSalao.getSegS()!=null) {
                            muda(true, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            txtSegE.getEditText().setText(configSalao.getSegE());
                            txtSegS.getEditText().setText(configSalao.getSegS());
                        }
                        else
                        {
                            muda(false, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            btStatusSegunda.setChecked(false);
                        }

                        if(configSalao.getTerE()!=null && configSalao.getTerS()!=null) {
                            muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            txtTerE.getEditText().setText(configSalao.getTerE());
                            txtTerS.getEditText().setText(configSalao.getTerS());
                        }
                        else
                        {
                            muda(false,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            btStatusTerca.setChecked(false);
                        }


                        if(configSalao.getQuaE()!=null && configSalao.getQuaS()!=null) {
                            muda(true,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            txtQuaE.getEditText().setText(configSalao.getQuaE());
                            txtQuaS.getEditText().setText(configSalao.getQuaS());
                        }
                        else
                        {
                            muda(false,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            btStatusQuarta.setChecked(false);
                        }

                        if(configSalao.getQuiE()!=null && configSalao.getQuiS()!=null) {
                            muda(true,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            txtQuiE.getEditText().setText(configSalao.getQuaE());
                            txtQuiS.getEditText().setText(configSalao.getQuiS());
                        }
                        else
                        {
                            muda(false,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            btStatusQuinta.setChecked(false);
                        }

                        if(configSalao.getSexE()!=null && configSalao.getSexS()!=null) {
                            muda(true,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            txtSexE.getEditText().setText(configSalao.getSexE());
                            txtSexS.getEditText().setText(configSalao.getSexS());
                        }
                        else
                        {
                            muda(false,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            btStatusSexta.setChecked(false);
                        }


                        if(configSalao.getSabE()!=null && configSalao.getSabS()!=null) {
                            muda(true,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            txtSabE.getEditText().setText(configSalao.getSabE());
                            txtSabS.getEditText().setText(configSalao.getSabS());
                        }
                        else
                        {
                            muda(false,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            btStatusSabado.setChecked(false);
                        }

                        if(configSalao.getDomE()!=null && configSalao.getDomS()!=null) {
                            muda(true,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            txtDomE.getEditText().setText(configSalao.getDomE());
                            txtDomS.getEditText().setText(configSalao.getDomS());
                        }
                        else
                        {
                            muda(false,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            btStatusDomingo.setChecked(false);
                        }



                       // String[] time = configSalao.getTempoReserva().split(":");
                       // Log.d("xex",configSalao.getTempoReserva().toString());
                       // Log.d("xex",configSalao.getTempoMinAgenda());

                       /* for(int i= 0; i < intervaloAgenda.getAdapter().getCount(); i++)
                        {
                            if(intervaloAgenda.getAdapter().getItem(i).toString().contains(configSalao.getTempoReserva()))
                            {
                                intervaloAgenda.setSelection(i);
                            }
                        }

                        for(int i= 0; i < tempoMinimo.getAdapter().getCount(); i++)
                        {
                            if(tempoMinimo.getAdapter().getItem(i).toString().contains(configSalao.getTempoMinAgenda()))
                            {
                                tempoMinimo.setSelection(i);
                            }
                        }*/

                        break;


                    case 400:
                        switch (response.message())
                        {
                            case "01":
                                Toast.makeText(configuracaoSalao.this, "não encontrado !!", Toast.LENGTH_LONG).show();
                                break;

                            case "02":
                                Toast.makeText(configuracaoSalao.this, "Parametros incorretos !!", Toast.LENGTH_LONG).show();
                                break;
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ConfiguracaoSalao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    pegarConfiguracao(idSalao);
                }
                else {
                    loading.fechar();
                    Log.d("xex","erro");
                    Log.d("xex",t.getMessage());
                }
            }
        });

    }
    //-------------------------------------------------------

    void muda(boolean status,TextInputLayout idtxtE,TextInputLayout idtxtS,ImageButton btE, ImageButton btS,ToggleButton btfolga)
    {
        if(status)
        {
            btfolga.setBackground(getResources().getDrawable(R.drawable.bt_folga_on));
            idtxtE.setClickable(true);
            idtxtE.setAlpha(1);
            idtxtS.setClickable(true);
            idtxtS.setAlpha(1);
            btE.setEnabled(true);
            btE.setClickable(true);
            btE.setAlpha(1f);
            btS.setEnabled(true);
            btS.setClickable(true);
            btS.setAlpha(1f);
        }
        else
        {
            btfolga.setBackground(getResources().getDrawable(R.drawable.bt_folga_off));
            idtxtE.setClickable(false);
            idtxtE.setAlpha(.3f);
            idtxtS.setClickable(false);
            idtxtS.setAlpha(.3f);
            btE.setEnabled(false);
            btE.setClickable(false);
            btE.setAlpha(.3f);
            btS.setEnabled(false);
            btS.setClickable(false);
            btS.setAlpha(.3f);
        }
    }

    public void showTimePickerDialog(View v,int idCampo) {
        timerPick tim = new timerPick();
        tim.setId(idCampo);
        DialogFragment newFragment = tim;
        newFragment.show(getFragmentManager(), "timePicker");
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
