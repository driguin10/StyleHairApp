package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView status;
    CardView CardStatus;
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

        status = (TextView) itemView.findViewById(R.id.Txtstatus);
        CardStatus = (CardView) itemView.findViewById(R.id.card_status);

        card.setOnClickListener(this);
        ListaMeuAgendamento = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        meuAgendamento = ListaMeuAgendamento.get(position);

if(meuAgendamento.getStatus()!=3) {
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
else
{
    new AlertDialog.Builder(contexto)
            .setTitle("Salão indisponivel !!")
            .setMessage("Este agendamento foi cancelado devido ao encerramento de conta do salão, deseja remover da lista este agendamento?")
            .setIcon(R.drawable.icone_delete)
            .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            })
            .setNegativeButton("não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .show();
}

    }
}
