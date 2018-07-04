package com.stylehair.nerdsolutions.stylehair.telas.meuSalao;


import android.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;
import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.telas.configuracaoApp;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.funcionarios;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.troca_gerente.trocar_gerente;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    String idUsuario;
    String idSalao;

    Button salvarConfig;
    Button trocarGerente;
    Button excluirSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    int qtTentativasSalvar = 3;
    int qtTentativaRealizadaSalvar = 0;
    int qtTentativaRealizadaExcluir = 0;
    SharedPreferences getSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_salao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_config_salao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configuração do Salão");
        Drawable upArrow = ContextCompat.getDrawable(configuracaoSalao.this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(configuracaoSalao.this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        loading = new Loading(configuracaoSalao.this);
        salvarConfig = (Button) findViewById(R.id.bt_salvarConfiguracao);
        trocarGerente = (Button) findViewById(R.id.btTrocaGerente);
        excluirSalao = (Button) findViewById(R.id.bt_deletar_salao);
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

        trocarGerente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(configuracaoSalao.this,trocar_gerente.class);
                intent.putExtra("idSalao",idSalao);
                startActivity(intent);
            }
        });

        horaSegE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_segE);
                txtSegE.getEditText().setError(null);
            }
        });

        horaSegS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_segS);
                txtSegS.getEditText().setError(null);
            }
        });

        horaTerE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_terE);
                txtTerE.getEditText().setError(null);
            }
        });

        horaTerS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_terS);
                txtTerS.getEditText().setError(null);
            }
        });

        horaQuaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quaE);
                txtQuaE.getEditText().setError(null);
            }
        });

        horaQuaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quaS);
                txtQuaS.getEditText().setError(null);
            }
        });

        horaQuiE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quiE);
                txtQuiE.getEditText().setError(null);
            }
        });

        horaQuiS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_quiS);
                txtQuiS.getEditText().setError(null);
            }
        });

        horaSexE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sexE);
                txtSexE.getEditText().setError(null);
            }
        });

        horaSexS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sexS);
                txtSexS.getEditText().setError(null);
            }
        });

        horaSabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sabE);
                txtSabE.getEditText().setError(null);
            }
        });

        horaSabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_sabS);
                txtSabS.getEditText().setError(null);
            }
        });

        horaDomE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_domE);
                txtDomE.getEditText().setError(null);
            }
        });

        horaDomS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_hora_domS);
                txtDomS.getEditText().setError(null);
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

        salvarConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(verificaCampos()) {
                    loading.abrir("Aguarde...");
                    editarConfiguracao(idSalao);
                }
                else
                {
                    Toast.makeText(configuracaoSalao.this,"Preencha os campos corretamente!!",Toast.LENGTH_LONG).show();
                }
            }
        });

        excluirSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(configuracaoSalao.this)
                        .setTitle("Deseja excluir este salão?")
                        .setMessage("Todas informações relacionadas a este salão seram excluidos!!")
                        .setIcon(R.drawable.icone_delete)
                        .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loading.abrir("Aguarde...");
                                String idUsuario = getSharedPreferences.getString("idUsuario", "-1");
                                String idFuncionario = getSharedPreferences.getString("idFuncionario", "-1");
                                String idSalao = getSharedPreferences.getString("idSalao", "-1");
                                encerrarSalao(idUsuario,idFuncionario,idSalao);
                            }
                        })
                        .setNegativeButton("não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

        //-------pega o id do login para fazer a consulta---------------
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        idUsuario = getSharedPreferences.getString("idUsuario", "-1");
        //---------------------------------------------------------------
        VerificaConexao verificaConexao = new VerificaConexao();
        if(verificaConexao.verifica(configuracaoSalao.this)) {
            loading.abrir("Aguarde...");
            pegarConfiguracao(idUsuario);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

public void editarConfiguracao(String id_Salao)
{

   String[] intervaloA =  intervaloAgenda.getSelectedItem().toString().split(" ");
   String tempoIntervalo = "";
   if(intervaloA[1].equals("hora"))
   {
       tempoIntervalo = intervaloA[0] +":00";
   }
   else
   {
       tempoIntervalo = "00:"+intervaloA[0];
   }


    String[] tempoMinA =  tempoMinimo.getSelectedItem().toString().split(" ");
    String tempoM = "";
    if(tempoMinA[1].equals("hora"))
    {
        tempoM = tempoMinA[0] +":00";
    }
    else
    {
        tempoM = "00:"+tempoMinA[0];
    }

    RequestBody IdSalao = RequestBody.create(MediaType.parse("text/plain"),id_Salao);

    RequestBody intervalo_Agenda = RequestBody.create(MediaType.parse("text/plain"),tempoIntervalo);
    RequestBody tempo_Minimo = RequestBody.create(MediaType.parse("text/plain"),tempoM);

    RequestBody SegE = RequestBody.create(MediaType.parse("text/plain"),txtSegE.getEditText().getText().toString());
    RequestBody SegS = RequestBody.create(MediaType.parse("text/plain"),txtSegS.getEditText().getText().toString());

    RequestBody TerE = RequestBody.create(MediaType.parse("text/plain"),txtTerE.getEditText().getText().toString());
    RequestBody TerS = RequestBody.create(MediaType.parse("text/plain"),txtTerS.getEditText().getText().toString());

    RequestBody QuaE = RequestBody.create(MediaType.parse("text/plain"),txtQuaE.getEditText().getText().toString());
    RequestBody QuaS = RequestBody.create(MediaType.parse("text/plain"),txtQuaS.getEditText().getText().toString());

    RequestBody QuiE = RequestBody.create(MediaType.parse("text/plain"),txtQuiE.getEditText().getText().toString());
    RequestBody QuiS = RequestBody.create(MediaType.parse("text/plain"),txtQuiS.getEditText().getText().toString());

    RequestBody SexE = RequestBody.create(MediaType.parse("text/plain"),txtSexE.getEditText().getText().toString());
    RequestBody SexS = RequestBody.create(MediaType.parse("text/plain"),txtSexS.getEditText().getText().toString());

    RequestBody SabE = RequestBody.create(MediaType.parse("text/plain"),txtSabE.getEditText().getText().toString());
    RequestBody SabS = RequestBody.create(MediaType.parse("text/plain"),txtSabS.getEditText().getText().toString());

    RequestBody DomE = RequestBody.create(MediaType.parse("text/plain"),txtDomE.getEditText().getText().toString());
    RequestBody DomS = RequestBody.create(MediaType.parse("text/plain"),txtDomS.getEditText().getText().toString());

    IApi iApi = IApi.retrofit.create(IApi.class);
    final Call<ResponseBody> callEditaConfiguracao = iApi.EditarConfiguracoesSalao(IdSalao,intervalo_Agenda,tempo_Minimo,SegE,SegS,TerE,TerS,QuaE,QuaS,QuiE,QuiS,SexE,SexS,SabE,SabS,DomE,DomS);
    callEditaConfiguracao.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            loading.fechar();
            callEditaConfiguracao.cancel();
            switch (response.code()){
                case 204:
                    Toast.makeText(configuracaoSalao.this,"Editado com sucesso !!",Toast.LENGTH_LONG).show();
                    break;

                case 400:
                    switch (response.message())
                    {
                        case "02":
                            Toast.makeText(configuracaoSalao.this,"Parametros incorretos !!",Toast.LENGTH_LONG).show();
                            break;

                        case "04":
                            Toast.makeText(configuracaoSalao.this,"Erro ao editar !!",Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            if (qtTentativaRealizadaSalvar < qtTentativasSalvar) {
                qtTentativaRealizadaSalvar++;

                editarConfiguracao(idUsuario);
            }
            else {
                loading.fechar();
            }
        }
    });
}



public boolean verificaCampos(){
      boolean status = true;

      if(txtSegE.isClickable())
      {
         if(txtSegE.getEditText().getText().toString().equals("")) {
             txtSegE.getEditText().setError("*");
             status = false;
         }
      }

      if(txtSegS.isClickable())
      {
          if(txtSegS.getEditText().getText().toString().equals("")) {
              txtSegS.getEditText().setError("*");
              status = false;
          }

      }

        if(txtTerE.isClickable())
        {
            if(txtTerE.getEditText().getText().toString().equals("")) {
                txtTerE.getEditText().setError("*");
                status = false;
            }

        }

        if(txtTerS.isClickable())
        {
            if(txtTerS.getEditText().getText().toString().equals("")) {
                txtTerS.getEditText().setError("*");
                status = false;
            }

        }

        if(txtQuaE.isClickable())
        {
            if(txtQuaE.getEditText().getText().toString().equals("")) {
                txtQuaE.getEditText().setError("*");
                status = false;
            }

        }

        if(txtQuaS.isClickable())
        {
            if(txtQuaS.getEditText().getText().toString().equals("")) {
                txtQuaS.getEditText().setError("*");
                status = false;
            }

        }


        if(txtQuiE.isClickable())
        {
            if(txtQuiE.getEditText().getText().toString().equals("")) {
                txtQuiE.getEditText().setError("*");
                status = false;
            }

        }


        if(txtQuiS.isClickable())
        {
            if(txtQuiS.getEditText().getText().toString().equals("")) {
                txtQuiS.getEditText().setError("*");
                status = false;
            }

        }


        if(txtSexE.isClickable())
        {
            if(txtSexE.getEditText().getText().toString().equals("")) {
                txtSexE.getEditText().setError("*");
                status = false;
            }

        }


        if(txtSexS.isClickable())
        {
            if(txtSexS.getEditText().getText().toString().equals("")) {
                txtSexS.getEditText().setError("*");
                status = false;
            }

        }

        if(txtSabE.isClickable())
        {
            if(txtSabE.getEditText().getText().toString().equals("")) {
                txtSabE.getEditText().setError("*");
                status = false;
            }
        }


        if(txtSabS.isClickable())
        {
            if(txtSabS.getEditText().getText().toString().equals("")) {
                txtSabS.getEditText().setError("*");
                status = false;
            }

        }

        if(txtDomE.isClickable())
        {
            if(txtDomE.getEditText().getText().toString().equals("")) {
                txtDomE.getEditText().setError("*");
                status = false;
            }
        }


        if(txtDomS.isClickable())
        {
            if(txtDomS.getEditText().getText().toString().equals("")) {
                txtDomS.getEditText().setError("*");
                status = false;
            }
        }
      return status;
}




    public void pegarConfiguracao(final String id){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Salao>> callBuscaConfiguracao = iApi.BuscaSalao(Integer.valueOf(id));
        callBuscaConfiguracao.enqueue(new Callback<List<Salao>>() {
            @Override
            public void onResponse(Call<List<Salao>>call, Response<List<Salao>> response) {
                loading.fechar();
                qtTentativaRealizada = 0 ;
                callBuscaConfiguracao.cancel();
                switch (response.code()) {
                    case 200:

                        List<Salao> saloes = response.body();
                        Salao salao = saloes.get(0);

                        idSalao = String.valueOf(salao.getIdSalao());

                        if(salao.getAgendamento() == 1)
                        {
                            intervaloAgenda.setClickable(true);
                            intervaloAgenda.setEnabled(true);
                            intervaloAgenda.setAlpha(.9f);

                            tempoMinimo.setAlpha(.9f);
                            tempoMinimo.setClickable(true);
                            tempoMinimo.setEnabled(true);
                        }
                        else
                        {
                            intervaloAgenda.setClickable(false);
                            intervaloAgenda.setEnabled(false);
                            intervaloAgenda.setAlpha(.3f);

                            tempoMinimo.setAlpha(.3f);
                            tempoMinimo.setClickable(false);
                            tempoMinimo.setEnabled(false);
                        }

                        if(salao.getSegE()!=null && salao.getSegS()!=null) {
                            muda(true, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            txtSegE.getEditText().setText(salao.getSegE().substring(0,5));
                            txtSegS.getEditText().setText(salao.getSegS().substring(0,5));
                        }
                        else
                        {
                            muda(false, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            btStatusSegunda.setChecked(false);
                        }

                        if(salao.getTerE()!=null && salao.getTerS()!=null) {
                            muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            txtTerE.getEditText().setText(salao.getTerE().substring(0,5));
                            txtTerS.getEditText().setText(salao.getTerS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            btStatusTerca.setChecked(false);
                        }


                        if(salao.getQuaE()!=null && salao.getQuaS()!=null) {
                            muda(true,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            txtQuaE.getEditText().setText(salao.getQuaE().substring(0,5));
                            txtQuaS.getEditText().setText(salao.getQuaS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            btStatusQuarta.setChecked(false);
                        }

                        if(salao.getQuiE()!=null && salao.getQuiS()!=null) {
                            muda(true,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            txtQuiE.getEditText().setText(salao.getQuaE().substring(0,5));
                            txtQuiS.getEditText().setText(salao.getQuiS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            btStatusQuinta.setChecked(false);
                        }

                        if(salao.getSexE()!=null && salao.getSexS()!=null) {
                            muda(true,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            txtSexE.getEditText().setText(salao.getSexE().substring(0,5));
                            txtSexS.getEditText().setText(salao.getSexS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            btStatusSexta.setChecked(false);
                        }


                        if(salao.getSabE()!=null && salao.getSabS()!=null) {
                            muda(true,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            txtSabE.getEditText().setText(salao.getSabE().substring(0,5));
                            txtSabS.getEditText().setText(salao.getSabS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            btStatusSabado.setChecked(false);
                        }

                        if(salao.getDomE()!=null && salao.getDomS()!=null) {
                            muda(true,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            txtDomE.getEditText().setText(salao.getDomE().substring(0,5));
                            txtDomS.getEditText().setText(salao.getDomS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            btStatusDomingo.setChecked(false);
                        }



                        String[] timetempoReserva = salao.getTempoReserva().split(":");
                        String comparaTimetempoReserva = "";
                        if(timetempoReserva[0].equals("01"))
                            comparaTimetempoReserva = "1 hora";
                        else
                            comparaTimetempoReserva = timetempoReserva[1] + " minutos";

                        for(int i= 0; i < intervaloAgenda.getAdapter().getCount(); i++)
                        {
                            if(intervaloAgenda.getAdapter().getItem(i).toString().contains(comparaTimetempoReserva))
                            {
                                intervaloAgenda.setSelection(i);
                            }
                        }


                        String[] timeTempoMinimo = salao.getTempoMinAgenda().split(":");
                        String comparaTempoMinimo = "";
                        if(timeTempoMinimo[0].equals("01"))
                            comparaTempoMinimo = "1 hora";
                        else
                            comparaTempoMinimo = timeTempoMinimo[1] + " minutos";
                        for(int i= 0; i < tempoMinimo.getAdapter().getCount(); i++)
                        {
                            if(tempoMinimo.getAdapter().getItem(i).toString().contains(comparaTempoMinimo))
                            {
                                tempoMinimo.setSelection(i);
                            }
                        }

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
            public void onFailure(Call<List<Salao>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    pegarConfiguracao(idUsuario);
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
            idtxtE.getEditText().setText("");
            idtxtS.getEditText().setText("");
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

    public void encerrarSalao(final String id_usuario,final String id_funcionario,final String id_gerente) {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiContaGerente = iApi.EncerrarGerenteLogin(id_usuario, id_funcionario, id_gerente);
        callExcluiContaGerente.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                qtTentativaRealizadaExcluir = 0;
                callExcluiContaGerente.cancel();
                switch (response.code()) {
                    case 200:
                        //Logout logout = new Logout();
                       //logout.deslogar(configuracaoApp.this, false);

                        finish();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaExcluir < qtTentativas) {
                    qtTentativaRealizadaExcluir++;
                    encerrarSalao(id_usuario, id_funcionario,id_gerente);
                } else {
                    loading.fechar();
                }
            }
        });
    }
}
