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
