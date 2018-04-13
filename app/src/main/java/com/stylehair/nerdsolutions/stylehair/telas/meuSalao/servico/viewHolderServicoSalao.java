package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderServicoSalao extends ViewHolder implements View.OnClickListener  {



    TextView NomeServico;
    TextView valor;
    CardView card;
    Context contexto;

    List<ServicoSalao> ListaServicoSalao;
    ServicoSalao servicoSalao;


    public viewHolderServicoSalao(View itemView, List<ServicoSalao> dados) {
        super(itemView);


        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        valor = (TextView) itemView.findViewById(R.id.valor_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServico);
        card.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);

        Intent intent = new Intent(v.getContext(),ver_servico_salao.class);
        intent.putExtra("idSalao", String.valueOf(servicoSalao.getIdSalao()));
        intent.putExtra("idServico",String.valueOf(servicoSalao.getIdServicoSalao()));
        intent.putExtra("servico",servicoSalao.getServico());
        v.getContext().startActivity(intent);
        ((Activity)v.getContext()).finish();

    }
}
