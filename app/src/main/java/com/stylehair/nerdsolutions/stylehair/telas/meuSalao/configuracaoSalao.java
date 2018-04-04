package com.stylehair.nerdsolutions.stylehair.telas.meuSalao;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;

import java.util.Calendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_config_salao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configuração do Salão");

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

        txtSegE.setEnabled(false);
        txtSegS.setEnabled(false);

        txtTerE.setEnabled(false);
        txtTerS.setEnabled(false);

        btStatusSegunda.setChecked(true);
        btStatusTerca.setChecked(true);

        muda(true,txtSegE,txtSegS,horaSegE,horaSegS,btStatusSegunda);
        muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);

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
    }

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
