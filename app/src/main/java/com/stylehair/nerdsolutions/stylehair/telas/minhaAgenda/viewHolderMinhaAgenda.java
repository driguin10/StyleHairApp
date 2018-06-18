package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;
import com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.verAgenda.verAgendamento;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class viewHolderMinhaAgenda extends ViewHolder implements View.OnClickListener  {


    CircleImageView imagem;
    TextView Nome;
    TextView data;
    TextView hora;
    Context contexto;
    CardView card;
    List<MeuAgendamento> ListaMeuAgendamento;
    MeuAgendamento meuAgendamento;

    String tipo;


    public viewHolderMinhaAgenda(View itemView, List<MeuAgendamento> dados) {
        super(itemView);

        imagem = (CircleImageView) itemView.findViewById(R.id.imagem);
        Nome = (TextView) itemView.findViewById(R.id.Txtnome);
        data = (TextView) itemView.findViewById(R.id.Txtdata);
        hora = (TextView) itemView.findViewById(R.id.Txthora);
        card = (CardView) itemView.findViewById(R.id.cardsAgendamentos);
        card.setOnClickListener(this);
        ListaMeuAgendamento = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        meuAgendamento = ListaMeuAgendamento.get(position);


            Intent intent = new Intent(v.getContext(), verAgendamento.class);
            intent.putExtra("idAgenda", String.valueOf(meuAgendamento.getIdAgenda()));
            intent.putExtra("idUsuario", String.valueOf(meuAgendamento.getIdUsuario()));
            intent.putExtra("nome", meuAgendamento.getNome());
            intent.putExtra("imagem", meuAgendamento.getImagem());
            intent.putExtra("data", meuAgendamento.getData());
            intent.putExtra("horaIni", meuAgendamento.getHoraIni());
            intent.putExtra("horaFim", meuAgendamento.getHoraFim());
            intent.putExtra("status", String.valueOf(meuAgendamento.getStatus()));
            intent.putExtra("tipo", tipo);
            intent.putExtra("idFuncionario", String.valueOf(meuAgendamento.getIdFuncionario()));
            intent.putExtra("nomeFuncionario", meuAgendamento.getNomeFuncionario());
            intent.putExtra("imagemFuncionario", meuAgendamento.getImagemFuncionario());
            intent.putExtra("endereco", meuAgendamento.getEndereco());
            intent.putExtra("numero", meuAgendamento.getNumero());
            intent.putExtra("bairro", meuAgendamento.getBairro());
            intent.putExtra("cidade", meuAgendamento.getCidade());
            intent.putExtra("complemento", meuAgendamento.getComplemento());
            intent.putExtra("estado", meuAgendamento.getEstado());
            ((Activity) v.getContext()).startActivityForResult(intent, 1);


    }
}
