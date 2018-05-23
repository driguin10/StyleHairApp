package com.stylehair.nerdsolutions.stylehair.telas;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Logout;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolherServico;
import com.stylehair.nerdsolutions.stylehair.telas.busca.busca_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.notificacoes.Notificacao;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_principal_gerente extends Fragment {



CardView btAbrir;
CardView btFechar;
CardView btAlmoco;

CardView btAgendaDia;
CardView btAgendar;
CardView btNotificar;
CardView btPesquisaSalao;

Loading loading;
    SharedPreferences getSharedPreferences;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

    String idSalao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_principal_gerente, container, false);
        getActivity().setTitle("Bem Vindo");
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getContext());

        idSalao = getSharedPreferences.getString("idSalao", "-1");

        loading = new Loading(getActivity());


        btAbrir= (CardView) view.findViewById(R.id.card_aberto);
        btFechar= (CardView) view.findViewById(R.id.card_fechado);
        btAlmoco= (CardView) view.findViewById(R.id.card_almoco);

        btAgendaDia= (CardView) view.findViewById(R.id.card_bt1);
        btAgendar= (CardView) view.findViewById(R.id.card_bt2);
        btNotificar= (CardView) view.findViewById(R.id.card_bt3);
        btPesquisaSalao= (CardView) view.findViewById(R.id.card_bt4);

        btPesquisaSalao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), busca_salao.class);
                startActivity(intent);
            }

        });

        btAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),escolherServico.class);
                intent.putExtra("idSalao",idSalao);
                startActivity(intent);
            }

        });

        btAgendaDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), minha_agenda.class);
                startActivity(intent);
            }

        });

        btNotificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Notificacao.class);
                startActivity(intent);
            }

        });


        btAbrir.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              mudarStatus(1);
                return true;
            }
        });

        btFechar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              mudarStatus(0);
                return true;
            }
        });


        btAlmoco.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               mudarStatus(2);
                return true;
            }
        });

        statusSalao(0);
        return view;
    }

    public void statusSalao(int status)
    {

        if(status == 0)
        {
            btAbrir.setAlpha(.4f);
            btFechar.setAlpha(1f);
            btAlmoco.setAlpha(.4f);
        }
        else
        if(status == 1)
        {
            btAbrir.setAlpha(1f);
            btFechar.setAlpha(.4f);
            btAlmoco.setAlpha(.4f);
        }
        else
        {
            btAbrir.setAlpha(.4f);
            btFechar.setAlpha(.4f);
            btAlmoco.setAlpha(1f);
        }
    }





    public void mudarStatus(final int status)
    {
            RequestBody IDSALAO = RequestBody.create(MediaType.parse("text/plain"),idSalao);
            RequestBody STATUS = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(status));
            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callMudaStatus = iApi.EditarStatusSalao(IDSALAO,STATUS);
            callMudaStatus.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    loading.fechar();
                    qtTentativaRealizada = 0;
                    switch (response.code())
                    {
                        case 204:
                            statusSalao(status);
                            Toast.makeText(getContext(),"Status alterado com sucesso!!",Toast.LENGTH_LONG).show();
                            break;

                        case 400:

                            break;
                    }

                    callMudaStatus.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        mudarStatus(status);
                    } else {
                        loading.fechar();
                    }
                }
            });
        }
    }

