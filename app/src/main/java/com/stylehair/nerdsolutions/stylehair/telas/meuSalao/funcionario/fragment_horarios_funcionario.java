package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.VerificaConexao;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;
import com.stylehair.nerdsolutions.stylehair.classes.Funcionario;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_horarios_funcionario extends Fragment {


    private OnFragmentInteractionListener mListener;

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
    View view;
    Loading loading;
    String idSalao;
String idFuncionario;
    Button salvarConfig;


    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    int qtTentativasSalvar = 3;
    int qtTentativaRealizadaSalvar = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_fragment_horarios_funcionario, container, false);
        loading = new Loading(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle!=null)
        {
            idFuncionario = bundle.getString("idFuncionario");
        }
        salvarConfig = (Button) view.findViewById(R.id.ver_bt_salvarConfiguracao);
        txtSegE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_segE);
        txtSegS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_segS);
        btStatusSegunda = (ToggleButton)view. findViewById(R.id.ver_bt_folga_seg);
        horaSegE = (ImageButton)view. findViewById(R.id.ver_pesquisa_hora_segE);
        horaSegS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_segS);

        txtTerE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_terE);
        txtTerS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_terS);
        btStatusTerca = (ToggleButton) view.findViewById(R.id.ver_bt_folga_ter);
        horaTerE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_terE);
        horaTerS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_terS);

        txtQuaE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_quaE);
        txtQuaS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_quaS);
        btStatusQuarta = (ToggleButton) view.findViewById(R.id.ver_bt_folga_qua);
        horaQuaE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_quaE);
        horaQuaS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_quaS);

        txtQuiE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_quiE);
        txtQuiS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_quiS);
        btStatusQuinta = (ToggleButton) view.findViewById(R.id.ver_bt_folga_qui);
        horaQuiE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_quiE);
        horaQuiS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_quiS);

        txtSexE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_sexE);
        txtSexS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_sexS);
        btStatusSexta = (ToggleButton) view.findViewById(R.id.ver_bt_folga_sex);
        horaSexE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_sexE);
        horaSexS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_sexS);

        txtSabE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_sabE);
        txtSabS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_sabS);
        btStatusSabado = (ToggleButton) view.findViewById(R.id.ver_bt_folga_sab);
        horaSabE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_sabE);
        horaSabS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_sabS);

        txtDomE = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_domE);
        txtDomS = (TextInputLayout) view.findViewById(R.id.txt_ver_hora_domS);
        btStatusDomingo = (ToggleButton) view.findViewById(R.id.ver_bt_folga_dom);
        horaDomE = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_domE);
        horaDomS = (ImageButton) view.findViewById(R.id.ver_pesquisa_hora_domS);

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
                showTimePickerDialog(v,R.id.txt_ver_hora_segE);
                txtSegE.getEditText().setError(null);
            }
        });

        horaSegS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_segS);
                txtSegS.getEditText().setError(null);
            }
        });

        horaTerE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_terE);
                txtTerE.getEditText().setError(null);
            }
        });

        horaTerS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_terS);
                txtTerS.getEditText().setError(null);
            }
        });

        horaQuaE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_quaE);
                txtQuaE.getEditText().setError(null);
            }
        });

        horaQuaS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_quaS);
                txtQuaS.getEditText().setError(null);
            }
        });

        horaQuiE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_quiE);
                txtQuiE.getEditText().setError(null);
            }
        });

        horaQuiS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_quiS);
                txtQuiS.getEditText().setError(null);
            }
        });

        horaSexE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_sexE);
                txtSexE.getEditText().setError(null);
            }
        });

        horaSexS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_sexS);
                txtSexS.getEditText().setError(null);
            }
        });

        horaSabE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_sabE);
                txtSabE.getEditText().setError(null);
            }
        });

        horaSabS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_sabS);
                txtSabS.getEditText().setError(null);
            }
        });

        horaDomE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_domE);
                txtDomE.getEditText().setError(null);
            }
        });

        horaDomS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v,R.id.txt_ver_hora_domS);
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
                    editarHorarioFuncionario(idFuncionario);
                }
                else
                {
                    Toast.makeText(getContext(),"Preencha os campos corretamente!!",Toast.LENGTH_LONG).show();
                }
            }
        });


        VerificaConexao verificaConexao = new VerificaConexao();
        if(verificaConexao.verifica(getContext())) {
            loading.abrir("Aguarde...");
            pegaraHorarioFuncionario(idFuncionario);
        }
        else
        {
            Toast.makeText(getContext(), "Sem conexão com internet !!!", Toast.LENGTH_SHORT).show();

        }



        return  view;
    }



    public void pegaraHorarioFuncionario(final String id){

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Funcionario>> callBuscaFuncionario = iApi.BuscaFuncionario(id);
        callBuscaFuncionario.enqueue(new Callback<List<Funcionario>>() {
            @Override
            public void onResponse(Call<List<Funcionario>>call, Response<List<Funcionario>> response) {
                loading.fechar();
                qtTentativaRealizada = 0 ;

                callBuscaFuncionario.cancel();
                switch (response.code()) {
                    case 200:

                        List<Funcionario> funcionario = response.body();
                            Funcionario func = funcionario.get(0);




                        if(func.getSegE()!=null && func.getSegS()!=null) {
                            muda(true, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            txtSegE.getEditText().setText(func.getSegE().substring(0,5));
                            txtSegS.getEditText().setText(func.getSegS().substring(0,5));
                        }
                        else
                        {
                            muda(false, txtSegE, txtSegS, horaSegE, horaSegS, btStatusSegunda);
                            btStatusSegunda.setChecked(false);
                        }

                        if(func.getTerE()!=null && func.getTerS()!=null) {
                            muda(true,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            txtTerE.getEditText().setText(func.getTerE().substring(0,5));
                            txtTerS.getEditText().setText(func.getTerS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtTerE,txtTerS,horaTerE,horaTerS,btStatusTerca);
                            btStatusTerca.setChecked(false);
                        }


                        if(func.getQuaE()!=null && func.getQuaS()!=null) {
                            muda(true,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            txtQuaE.getEditText().setText(func.getQuaE().substring(0,5));
                            txtQuaS.getEditText().setText(func.getQuaS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtQuaE,txtQuaS,horaQuaE,horaQuaS,btStatusQuarta);
                            btStatusQuarta.setChecked(false);
                        }

                        if(func.getQuiE()!=null && func.getQuiS()!=null) {
                            muda(true,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            txtQuiE.getEditText().setText(func.getQuaE().substring(0,5));
                            txtQuiS.getEditText().setText(func.getQuiS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtQuiE,txtQuiS,horaQuiE,horaQuiS,btStatusQuinta);
                            btStatusQuinta.setChecked(false);
                        }

                        if(func.getSexE()!=null && func.getSexS()!=null) {
                            muda(true,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            txtSexE.getEditText().setText(func.getSexE().substring(0,5));
                            txtSexS.getEditText().setText(func.getSexS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtSexE,txtSexS,horaSexE,horaSexS,btStatusSexta);
                            btStatusSexta.setChecked(false);
                        }


                        if(func.getSabE()!=null && func.getSabS()!=null) {
                            muda(true,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            txtSabE.getEditText().setText(func.getSabE().substring(0,5));
                            txtSabS.getEditText().setText(func.getSabS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtSabE,txtSabS,horaSabE,horaSabS,btStatusSabado);
                            btStatusSabado.setChecked(false);
                        }

                        if(func.getDomE()!=null && func.getDomS()!=null) {
                            muda(true,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            txtDomE.getEditText().setText(func.getDomE().substring(0,5));
                            txtDomS.getEditText().setText(func.getDomS().substring(0,5));
                        }
                        else
                        {
                            muda(false,txtDomE,txtDomS,horaDomE,horaDomS,btStatusDomingo);
                            btStatusDomingo.setChecked(false);
                        }


                        break;


                    case 400:
                        switch (response.message())
                        {
                            case "01":
                                Toast.makeText(getContext(), "não encontrado !!", Toast.LENGTH_LONG).show();
                                break;

                            case "02":
                                Toast.makeText(getContext(), "Parametros incorretos !!", Toast.LENGTH_LONG).show();
                                break;
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Funcionario>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;

                    pegaraHorarioFuncionario(idFuncionario);
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



    public void editarHorarioFuncionario(String id_Salao)
    {


        RequestBody IdFuncionario = RequestBody.create(MediaType.parse("text/plain"),idFuncionario);


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
        final Call<ResponseBody> callEditaConfiguracao = iApi.EditarHoraFuncionario(IdFuncionario,SegE,SegS,TerE,TerS,QuaE,QuaS,QuiE,QuiS,SexE,SexS,SabE,SabS,DomE,DomS);
        callEditaConfiguracao.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.fechar();
                Log.d("xex",String.valueOf(response.code()) + "*" + response.message());
                callEditaConfiguracao.cancel();
                switch (response.code()){
                    case 204:
                        Toast.makeText(getContext(),"Editado com sucesso !!",Toast.LENGTH_LONG).show();
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "02":
                                Toast.makeText(getContext(),"Parametros incorretos !!",Toast.LENGTH_LONG).show();
                                break;

                            case "04":
                                Toast.makeText(getContext(),"Erro ao editar !!",Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizadaSalvar < qtTentativasSalvar) {
                    qtTentativaRealizadaSalvar++;

                    editarHorarioFuncionario(idSalao);
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
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
    }




    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
