package com.stylehair.nerdsolutions.stylehair.telas.minhaAgenda.verAgenda;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;
import java.util.Locale;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_servico_salao_agendado extends RecyclerView.Adapter<viewHolderServicoSalaoAgendado> {

    List<ServicoSalao> ListaServicoSalao;
    RecyclerView Lista;
    TextView vlTotal;


    public Adaptador_servico_salao_agendado(List<ServicoSalao> listaServicoSalao, RecyclerView lista,TextView VlTotal) {
        ListaServicoSalao = listaServicoSalao;
        Lista = lista;
        vlTotal = VlTotal;
    }


    @Override
    public viewHolderServicoSalaoAgendado onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_agendado, parent, false);
        return new viewHolderServicoSalaoAgendado(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoSalaoAgendado holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$ "+String.format(Locale.getDefault(),"%.2f", ListaServicoSalao.get(position).getValor()));
         holder.tempo.setText("Tempo "+ListaServicoSalao.get(position).getTempo());

        float vlCampo = 0;
         if(!vlTotal.getText().toString().equals(""))
         vlCampo = Float.valueOf(vlTotal.getText().toString());


         float vlPeca = ListaServicoSalao.get(position).getValor();
         vlTotal.setText(String.valueOf(vlCampo + vlPeca));
}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

