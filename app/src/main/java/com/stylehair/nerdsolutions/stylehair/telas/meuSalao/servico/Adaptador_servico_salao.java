package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.servico;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_servico_salao extends RecyclerView.Adapter<viewHolderServicoSalao> {

    List<ServicoSalao> ListaServicoSalao;
    RecyclerView Lista;



    public Adaptador_servico_salao(List<ServicoSalao> listaServicoSalao, RecyclerView lista) {
        ListaServicoSalao = listaServicoSalao;
        Lista = lista;
    }


    @Override
    public viewHolderServicoSalao onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico, parent, false);
        return new viewHolderServicoSalao(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoSalao holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.valueOf(ListaServicoSalao.get(position).getValor()));
         holder.Lista = Lista;
}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

