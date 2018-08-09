package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.verAgenda;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class viewHolderServicoSalaoAgendado extends ViewHolder  {

    TextView NomeServico;
    TextView valor;
    TextView tempo;
    CardView card;
    RecyclerView Lista;

    public viewHolderServicoSalaoAgendado(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        tempo = (TextView) itemView.findViewById(R.id.tempo_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServico);
    }
    }

