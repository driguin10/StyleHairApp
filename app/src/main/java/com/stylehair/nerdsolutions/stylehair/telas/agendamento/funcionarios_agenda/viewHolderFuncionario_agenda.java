package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionarioBusca;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderFuncionario_agenda extends ViewHolder implements View.OnClickListener  {


    CircleImageView imagemFunc;
    TextView NomeFuncionario;
    TextView Servicos;
    CardView card;
    Context contexto;
    RecyclerView lista;

    List<UsuarioFuncionarioBusca> ListaUsuario;
    UsuarioFuncionarioBusca usuarioFuncionario;
    ArrayList<String> ListaIdServ = new ArrayList<>();

    public viewHolderFuncionario_agenda(View itemView, List<UsuarioFuncionarioBusca> dados) {
        super(itemView);

        imagemFunc = (CircleImageView) itemView.findViewById(R.id.img_funcionario);
        NomeFuncionario = (TextView) itemView.findViewById(R.id.nome_funcionario);
        Servicos = (TextView) itemView.findViewById(R.id.txtServicos);
        card = (CardView) itemView.findViewById(R.id.cardsFunc);
        card.setOnClickListener(this);
        ListaUsuario = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        usuarioFuncionario = ListaUsuario.get(position);

        for(int x= 0; x< lista.getChildCount(); x++)
        {
            if(lista.getChildAt(x).findViewById(R.id.cardsFunc).isSelected())
            {
                CardView cv = (CardView) lista.getChildAt(x).findViewById(R.id.cardsFunc);
                cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corTextos));
                cv.setCardElevation(5);
                lista.getChildAt(x).findViewById(R.id.cardsFunc).setSelected(false);
                TextView textoNome = (TextView) lista.getChildAt(x).findViewById(R.id.nome_funcionario);
                textoNome.setTextColor(contexto.getResources().getColor(R.color.black_de));
            }
        }
        boolean selecionado = lista.getChildAt(position).findViewById(R.id.cardsFunc).isSelected();
        CardView cv = (CardView) lista.getChildAt(position).findViewById(R.id.cardsFunc);
        cv.setCardBackgroundColor(contexto.getResources().getColor(R.color.corItemEscolhido));
        cv.setCardElevation(7);
        lista.getChildAt(position).findViewById(R.id.cardsFunc).setSelected(true);
        TextView textoNome = (TextView) lista.getChildAt(position).findViewById(R.id.nome_funcionario);
        textoNome.setTextColor(contexto.getResources().getColor(R.color.corToobar));

    }
}
