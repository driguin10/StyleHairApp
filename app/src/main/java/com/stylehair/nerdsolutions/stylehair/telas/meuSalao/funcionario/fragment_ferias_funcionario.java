package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.auxiliar.datePick;
import com.stylehair.nerdsolutions.stylehair.auxiliar.timerPick;
import com.stylehair.nerdsolutions.stylehair.classes.FeriasFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.minhaConta.fragmentUsuario;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_ferias_funcionario extends Fragment {
    ImageButton BTferiasIni;
    ImageButton BTferiasFim;
    TextInputLayout TXTferiasIni;
    TextInputLayout TXTferiasFim;
    Button BTsalvar;
    Button BTCancelar;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int qtTentativaBusca = 0;
    String idFuncionario;
    Loading loading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_ferias_funcionario, container, false);
        loading = new Loading(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle!=null)
        {
            idFuncionario = bundle.getString("idFuncionario");
        }
        BTsalvar = (Button) view.findViewById(R.id.bt_salvar);
        BTCancelar = (Button) view.findViewById(R.id.bt_cancelar);
        TXTferiasIni = (TextInputLayout) view.findViewById(R.id.txt_dataini);
        TXTferiasFim = (TextInputLayout) view.findViewById(R.id.txt_txt_datafim);
        BTferiasIni = (ImageButton) view. findViewById(R.id.pesquisa_dataIni);
        BTferiasFim = (ImageButton) view. findViewById(R.id.pesquisa_dataFim);

        BTferiasIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v,R.id.txt_dataini);
            }
        });

        BTferiasFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v,R.id.txt_txt_datafim);
            }
        });


        BTCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TXTferiasIni.getEditText().setText("");
                TXTferiasFim.getEditText().setText("");
            }
        });

        BTsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String dtIni = TXTferiasIni.getEditText().getText().toString();
            String dtFim = TXTferiasFim.getEditText().getText().toString();
        if(dtIni.equals("") && !dtFim.equals("") || !dtIni.equals("") && dtFim.equals(""))
        {
            Toast.makeText(getContext(),"Preencha os campos corretamente",Toast.LENGTH_LONG).show();
        }
        else {
        if(!dtIni.equals("")||!dtFim.equals("")) {
            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date dateA = df.parse(TXTferiasIni.getEditText().getText().toString());
                Date dateB = df.parse(TXTferiasFim.getEditText().getText().toString());
                int compara = dateA.compareTo(dateB);
                if (compara != 1) {
                    loading.abrir("Aguarde...");
                    AtualizaFerias();
                } else {
                    Toast.makeText(getContext(), "Data inicial não pode ser menor que data Final!", Toast.LENGTH_LONG).show();
                }
            } catch (ParseException e) {
            }
        }else
        {
            loading.abrir("Aguarde...");
            AtualizaFerias();
        }
    }
            }
        });
        loading.abrir("Aguarde...");
        pegarFerias();
        return  view;
    }



    public void AtualizaFerias()
    {
            String  ini = TXTferiasIni.getEditText().getText().toString();
            String  fim = TXTferiasFim.getEditText().getText().toString();
            RequestBody INI = RequestBody.create(MediaType.parse("text/plain"),ini);
            RequestBody FIm = RequestBody.create(MediaType.parse("text/plain"),fim);
            RequestBody IDFuncionario = RequestBody.create(MediaType.parse("text/plain"),idFuncionario);
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callFerias = iApi.FeriasFuncionario(IDFuncionario,INI,FIm);
            callFerias.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.fechar();
                    qtTentativaRealizada = 0;
                    callFerias.cancel();
                    if(response.isSuccessful()) {
                        Toast.makeText(getContext(), "Ferias Atualizada!!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        switch (response.code())
                        {
                            case 400:
                                switch (response.message())
                                {
                                    case "02":
                                        Toast.makeText(getContext(),"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                        break;

                                    case "01":
                                        Toast.makeText(getContext(),"Funcionario não encontrado!!",Toast.LENGTH_LONG).show();
                                        break;
                                }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        AtualizaFerias();
                    } else {
                        loading.fechar();
                    }
                }
            });
        }


    public void pegarFerias()
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<FeriasFuncionario> callFerias = iApi.BuscaFeriasFuncionario(idFuncionario);
        callFerias.enqueue(new Callback<FeriasFuncionario>() {
            @Override
            public void onResponse(Call<FeriasFuncionario> call, Response<FeriasFuncionario> response) {
                loading.fechar();
                qtTentativaBusca = 0;
                callFerias.cancel();
                if(response.isSuccessful()) {
                    FeriasFuncionario feriasFuncionario = response.body();
                    if(feriasFuncionario.getFeriasIni()!=null) {
                        String[] feriasI = feriasFuncionario.getFeriasIni().split("-");
                        TXTferiasIni.getEditText().setText(feriasI[2]+"-"+feriasI[1]+"-"+feriasI[0]);
                    }

                    if(feriasFuncionario.getFeriasFim()!=null) {
                        String[] feriasF = feriasFuncionario.getFeriasFim().split("-");
                        TXTferiasFim.getEditText().setText(feriasF[2]+"-"+feriasF[1]+"-"+feriasF[0]);
                    }
                }
                else {
                    switch (response.code())
                    {
                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(getContext(),"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "01":
                                    Toast.makeText(getContext(),"Funcionario não encontrado!!",Toast.LENGTH_LONG).show();
                                    break;
                            }
                    }
                }
            }

            @Override
            public void onFailure(Call<FeriasFuncionario> call, Throwable t) {
                if (qtTentativaBusca < qtTentativas) {
                    qtTentativaBusca++;
                    pegarFerias();
                } else {
                    loading.fechar();
                }
            }
        });


    }


    public void showDatePickerDialog(View v,int idCampo) {
        datePick datPick = new datePick();
        datPick.setId(idCampo);
        android.support.v4.app.DialogFragment newFragment = datPick;
        newFragment.show(getFragmentManager(), "datePicker");
    }


    //------------------------------------------------------------

}
