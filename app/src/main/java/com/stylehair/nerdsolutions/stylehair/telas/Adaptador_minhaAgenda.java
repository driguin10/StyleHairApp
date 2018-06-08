package com.stylehair.nerdsolutions.stylehair.telas;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.MeuAgendamento;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderFuncionario;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_minhaAgenda extends RecyclerView.Adapter<viewHolderMinhaAgenda> {

    List<MeuAgendamento> meuAgendamentos;

    public Adaptador_minhaAgenda(List<MeuAgendamento> MeuAgendamentos) {
        meuAgendamentos = MeuAgendamentos;
    }


    @Override
    public viewHolderMinhaAgenda onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_meus_agendamentos, parent, false);
        return new viewHolderMinhaAgenda(vista,meuAgendamentos);
    }

    @Override
    public void onBindViewHolder(viewHolderMinhaAgenda holder, int position) {
         holder.Nome.setText(meuAgendamentos.get(position).getNome());


         String[] data = meuAgendamentos.get(position).getData().split("-");
         String newData = data[2]+"/"+data[1]+"/"+data[0];
         holder.data.setText(newData);
         holder.hora.setText(meuAgendamentos.get(position).getHoraIni());
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + meuAgendamentos.get(position).getImagem()).into(holder.imagem);
}
    @Override
    public int getItemCount() {
        return meuAgendamentos.size();
    }

}

