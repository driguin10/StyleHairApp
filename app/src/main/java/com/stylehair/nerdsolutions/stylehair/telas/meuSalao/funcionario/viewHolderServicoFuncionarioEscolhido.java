package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderServicoFuncionarioEscolhido extends ViewHolder implements View.OnClickListener  {

    TextView NomeServico;

    CardView card;
    Context contexto;
    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    Loading loading;
    String idF;


    public viewHolderServicoFuncionarioEscolhido(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServicoEscolhido);
        card.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);

        if(v.getId() == card.getId())
        {

        }
    }
    }

