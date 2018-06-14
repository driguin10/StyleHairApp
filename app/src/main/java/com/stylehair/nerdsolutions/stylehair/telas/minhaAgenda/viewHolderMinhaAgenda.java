package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;
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

       /* Intent intent = new Intent(v.getContext(),ver_funcionario.class);
        intent.putExtra("idUsuario", String.valueOf(usuarioFuncionario.getIdUsuario()));
        intent.putExtra("idFuncionario",String.valueOf(usuarioFuncionario.getIdFuncionario()));
        v.getContext().startActivity(intent);
        ((Activity)v.getContext()).finish();*/

    }
}
