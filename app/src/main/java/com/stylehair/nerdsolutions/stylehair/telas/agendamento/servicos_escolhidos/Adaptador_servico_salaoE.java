package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_escolhidos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.viewHolderescolherServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico.viewHolderServicoSalao;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_servico_salaoE extends RecyclerView.Adapter<viewHolderServicoSalao_escolhido> {

    List<ServicoSalao> ListaServicoSalao;



    public Adaptador_servico_salaoE(List<ServicoSalao> listaServicoSalao) {
        ListaServicoSalao = listaServicoSalao;
    }


    @Override
    public viewHolderServicoSalao_escolhido onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico, parent, false);
        return new viewHolderServicoSalao_escolhido(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoSalao_escolhido holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.valueOf(ListaServicoSalao.get(position).getValor()));

}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

