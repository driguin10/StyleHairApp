package com.stylehair.nerdsolutions.stylehair.telas.agendamento.funcionarios_agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.ver_funcionario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderFuncionario_agenda extends ViewHolder implements View.OnClickListener  {


    CircleImageView imagemFunc;
    TextView NomeFuncionario;
    CardView card;
    Context contexto;

    List<UsuarioFuncionario> ListaUsuario;
    UsuarioFuncionario usuarioFuncionario;


    public viewHolderFuncionario_agenda(View itemView, List<UsuarioFuncionario> dados) {
        super(itemView);

        imagemFunc = (CircleImageView) itemView.findViewById(R.id.img_funcionario);
        NomeFuncionario = (TextView) itemView.findViewById(R.id.nome_funcionario);
        card = (CardView) itemView.findViewById(R.id.cardsFunc);
        card.setOnClickListener(this);
        ListaUsuario = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        usuarioFuncionario = ListaUsuario.get(position);

       /* Intent intent = new Intent(v.getContext(),ver_funcionario.class);
        intent.putExtra("idUsuario", String.valueOf(usuarioFuncionario.getIdUsuario()));
        intent.putExtra("idFuncionario",String.valueOf(usuarioFuncionario.getIdFuncionario()));
        v.getContext().startActivity(intent);
        ((Activity)v.getContext()).finish();*/

    }
}