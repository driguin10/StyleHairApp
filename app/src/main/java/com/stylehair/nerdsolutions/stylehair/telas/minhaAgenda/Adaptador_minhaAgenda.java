package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;

import java.util.List;



public class Adaptador_minhaAgenda extends RecyclerView.Adapter<viewHolderMinhaAgenda> {
    List<MeuAgendamento> meuAgendamentos;
    Context contexto;
    String Tipo;
    public Adaptador_minhaAgenda(List<MeuAgendamento> MeuAgendamentos,String tipo) {
        meuAgendamentos = MeuAgendamentos;
        Tipo = tipo;
    }

    @Override
    public viewHolderMinhaAgenda onCreateViewHolder(ViewGroup parent, int viewType) {
        contexto = parent.getContext();
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_meus_agendamentos, parent, false);
        return new viewHolderMinhaAgenda(vista,meuAgendamentos);
    }

    @Override
    public void onBindViewHolder(viewHolderMinhaAgenda holder, int position) {
        if(meuAgendamentos.get(position).getNome() == null)
            holder.Nome.setText("Agendamento Cancelado, Salão indisponivel.");
        else
            holder.Nome.setText(meuAgendamentos.get(position).getNome());
         String[] data = meuAgendamentos.get(position).getData().split("-");
         String newData = data[2]+"/"+data[1]+"/"+data[0];
         holder.data.setText(newData);
         holder.hora.setText(meuAgendamentos.get(position).getHoraIni().substring(0,5));
         if(meuAgendamentos.get(position).getImagem() =="")
             holder.imagem.setImageDrawable(contexto.getResources().getDrawable(R.drawable.img_padrao_user));
         else
             Picasso.with(holder.contexto).load("http://stylehair.xyz/" + meuAgendamentos.get(position).getImagem()).into(holder.imagem);
         holder.tipo = Tipo;

         if(meuAgendamentos.get(position).getStatus() == 0 || meuAgendamentos.get(position).getStatus() == 3) {
             holder.CardStatus.setCardBackgroundColor(contexto.getResources().getColor(R.color.corFechado));
             holder.status.setText("Cancelado");
         }
         else
         if(meuAgendamentos.get(position).getStatus() == 1) {
             holder.CardStatus.setCardBackgroundColor(contexto.getResources().getColor(R.color.corAlmoco));
             holder.status.setText("Aguardando atentimento");
         }
         else
         if(meuAgendamentos.get(position).getStatus() == 2) {
             holder.CardStatus.setCardBackgroundColor(contexto.getResources().getColor(R.color.corAberto));
             holder.status.setText("Finalizado");
         }
    }
    @Override
    public int getItemCount() {
        return meuAgendamentos.size();
    }

}

