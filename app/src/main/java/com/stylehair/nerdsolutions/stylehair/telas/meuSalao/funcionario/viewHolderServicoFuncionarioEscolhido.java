package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

    int posicaoAnterior = -1;
    int poo = 0;

    RecyclerView lista;


    public viewHolderServicoFuncionarioEscolhido(View itemView, List<ServicoSalao> dados) {
        super(itemView);
        NomeServico = (TextView) itemView.findViewById(R.id.nome_servico);
        card = (CardView) itemView.findViewById(R.id.cardsServicoEscolhido);
        card.setOnClickListener(this);
        ListaServicoSalao = dados;
        contexto = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        servicoSalao = ListaServicoSalao.get(position);
        for(int x= 0; x< lista.getChildCount(); x++)
        {
            if(lista.getChildAt(x).findViewById(R.id.cardsServicoEscolhido).isSelected())
            {
                CardView cv = (CardView) lista.getChildAt(x).findViewById(R.id.cardsServicoEscolhido);
                cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corTextos));
                cv.setCardElevation(5);
                lista.getChildAt(x).findViewById(R.id.cardsServicoEscolhido).setSelected(false);
               TextView textoNome = (TextView) lista.getChildAt(x).findViewById(R.id.nome_servico);
               textoNome.setTextColor(contexto.getResources().getColor(R.color.black_de));
            }
        }
        boolean selecionado = lista.getChildAt(position).findViewById(R.id.cardsServicoEscolhido).isSelected();
        CardView cv = (CardView) lista.getChildAt(position).findViewById(R.id.cardsServicoEscolhido);
        cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corItemEscolhido));
        cv.setCardElevation(7);
        lista.getChildAt(position).findViewById(R.id.cardsServicoEscolhido).setSelected(true);
        TextView textoNome = (TextView) lista.getChildAt(position).findViewById(R.id.nome_servico);
        textoNome.setTextColor(contexto.getResources().getColor(R.color.corToobar));
    }
}

