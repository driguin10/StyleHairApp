package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_escolhidos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.servicos_salao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.ver_servico_salao;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderServicoSalao_escolhido extends ViewHolder implements View.OnClickListener  {



    TextView NomeServico;
    TextView valor;
    CardView card;
    Context contexto;
    ImageButton excluir;

    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;

Loading loading;

    public viewHolderServicoSalao_escolhido(View itemView, List<ServicoSalao> dados) {
        super(itemView);


        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServico);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_servico);
        card.setOnClickListener(this);

        excluir.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();

    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);

        if (v.getId() == excluir.getId())
        {
            new AlertDialog.Builder(contexto)
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir este serviço?")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            excluiServico(String.valueOf(servicoSalao.getIdServicoSalao()));

                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        else
        if(v.getId() == card.getId())
        {
            Intent intent = new Intent(v.getContext(),ver_servico_salao.class);
            intent.putExtra("idSalao", String.valueOf(servicoSalao.getIdSalao()));
            intent.putExtra("idServico",String.valueOf(servicoSalao.getIdServicoSalao()));
            intent.putExtra("servico",servicoSalao.getServico());
            intent.putExtra("valor",String.valueOf(servicoSalao.getValor()));
            intent.putExtra("tempo",servicoSalao.getTempo());
            intent.putExtra("sexo",servicoSalao.getSexo());
            v.getContext().startActivity(intent);
            ((Activity)v.getContext()).finish();
        }
    }


    public void excluiServico(String id)
    {



            IApi iApi = IApi.retrofit.create(IApi.class);
            final Call<ResponseBody> callExcluiServico = iApi.ExcluirServicosSalao(id);
            callExcluiServico.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    qtTentativaRealizada = 0;
                    switch (response.code())
                    {
                        case 204:
                            Intent intent = new Intent(contexto,servicos_salao.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            contexto.startActivity(intent);
                            ((Activity)contexto).finish();
                            break;

                        case 400:
                            switch (response.message())
                            {
                                case "02":
                                    Toast.makeText(contexto,"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                    break;

                                case "05":
                                    Toast.makeText(contexto,"Erro ao excluir!!",Toast.LENGTH_LONG).show();
                                    break;
                            }
                            break;
                    }

                    callExcluiServico.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {


                    if (qtTentativaRealizada < qtTentativas) {
                        qtTentativaRealizada++;
                        excluiServico(String.valueOf(servicoSalao.getIdServicoSalao()));
                    } else {


                        if (t instanceof IOException) {
                            Log.d("xex", "this is an actual network failure timeout:( inform the user and possibly retry");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else if (t instanceof IllegalStateException) {
                            Log.d("xex", "ConversionError");
                            Log.d("xex", String.valueOf(t.getCause()));
                        } else {
                            Log.d("xex", "erro");
                            Log.d("xex", String.valueOf(t.getCause()));
                            Log.d("xex", String.valueOf(t.getLocalizedMessage()));
                        }

                    }




                }
            });
        }
    }

