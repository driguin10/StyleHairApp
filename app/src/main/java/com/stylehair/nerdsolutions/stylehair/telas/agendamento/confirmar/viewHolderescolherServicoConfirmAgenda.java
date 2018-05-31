package com.stylehair.nerdsolutions.stylehair.telas.agendamento.confirmar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.auxiliar.Loading;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda.escolherFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido.lista_servi_escolhido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderescolherServicoConfirmAgenda extends ViewHolder  {

    TextView NomeServico;
    TextView valor;
    List<String> ListaServicoSalao;

    public viewHolderescolherServicoConfirmAgenda(View itemView, List<String> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        ListaServicoSalao = dados;
    }

}

