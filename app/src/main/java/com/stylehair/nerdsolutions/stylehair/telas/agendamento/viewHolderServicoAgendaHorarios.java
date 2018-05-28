package com.stylehair.nerdsolutions.stylehair.telas.agendamento;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.ArrayList;
import java.util.List;

public class viewHolderServicoAgendaHorarios extends ViewHolder implements View.OnClickListener  {
    TextView hora;
    CardView card;
    Context contexto;
    ArrayList<String> ListaHorario;
    RecyclerView lista;

    public viewHolderServicoAgendaHorarios(View itemView,  ArrayList<String> dados) {
        super(itemView);
        hora = (TextView) itemView.findViewById(R.id.horario);
        card = (CardView) itemView.findViewById(R.id.cardsHorarioEscolhido);
        card.setOnClickListener(this);
        ListaHorario = dados;
        contexto = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
       // servicoSalao = ListaServicoSalao.get(position);
        for(int x= 0; x< lista.getChildCount(); x++)
        {
            if(lista.getChildAt(x).findViewById(R.id.cardsHorarioEscolhido).isSelected())
            {
                CardView cv = (CardView) lista.getChildAt(x).findViewById(R.id.cardsHorarioEscolhido);
                cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corTextos));
                cv.setCardElevation(5);
                lista.getChildAt(x).findViewById(R.id.cardsHorarioEscolhido).setSelected(false);
               TextView textoNome = (TextView) lista.getChildAt(x).findViewById(R.id.horario);
               textoNome.setTextColor(contexto.getResources().getColor(R.color.black_de));
            }
        }
        boolean selecionado = lista.getChildAt(position).findViewById(R.id.cardsHorarioEscolhido).isSelected();
        CardView cv = (CardView) lista.getChildAt(position).findViewById(R.id.cardsHorarioEscolhido);
        cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corItemEscolhido));
        cv.setCardElevation(7);
        lista.getChildAt(position).findViewById(R.id.cardsHorarioEscolhido).setSelected(true);
        TextView textoNome = (TextView) lista.getChildAt(position).findViewById(R.id.horario);
        textoNome.setTextColor(contexto.getResources().getColor(R.color.corToobar));
    }
}

