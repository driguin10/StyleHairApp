package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_ecolherservico_salao extends RecyclerView.Adapter<viewHolderescolherServicoSalao> {

    List<ServicoSalao> ListaServicoSalao;



    public Adaptador_ecolherservico_salao(List<ServicoSalao> listaServicoSalao) {
        ListaServicoSalao = listaServicoSalao;
    }


    @Override
    public viewHolderescolherServicoSalao onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_grid, parent, false);
        return new viewHolderescolherServicoSalao(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderescolherServicoSalao holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.valueOf(ListaServicoSalao.get(position).getValor()));

}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

