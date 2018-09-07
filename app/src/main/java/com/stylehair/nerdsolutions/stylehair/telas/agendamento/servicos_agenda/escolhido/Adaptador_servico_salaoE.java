package com.stylehair.nerdsolutions.stylehair.telas.agendamento.servicos_agenda.escolhido;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;
import java.util.Locale;


public class Adaptador_servico_salaoE extends RecyclerView.Adapter<viewHolderServicoSalao_escolhido> {
    List<ServicoSalao> ListaServicoSalao;
    RecyclerView Rlista;
    Button BtInfo;

    public Adaptador_servico_salaoE(List<ServicoSalao> listaServicoSalao, RecyclerView lista,Button bt_info) {
        ListaServicoSalao = listaServicoSalao;
        Rlista = lista;
        BtInfo = bt_info;
    }


    @Override
    public viewHolderServicoSalao_escolhido onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico, parent, false);
        return new viewHolderServicoSalao_escolhido(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoSalao_escolhido holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$ "+String.format(Locale.getDefault(),"%.2f", ListaServicoSalao.get(position).getValor()));
         holder.Rlista = Rlista;
         holder.btinfo = BtInfo;

}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }


}

