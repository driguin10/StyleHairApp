package com.stylehair.nerdsolutions.stylehair.telas.busca.busca_servicos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderServicoFuncionario;

import java.util.List;


public class Adaptador_servico_funcionario_busca extends RecyclerView.Adapter<viewHolderServicoFuncionario_busca> {

    List<ServicoSalao> ListaServicoSalao;

    public Adaptador_servico_funcionario_busca(List<ServicoSalao> listaServicoSalao) {
        ListaServicoSalao = listaServicoSalao;
    }


    @Override
    public viewHolderServicoFuncionario_busca onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_agenda, parent, false);
        return new viewHolderServicoFuncionario_busca(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoFuncionario_busca holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.valueOf(ListaServicoSalao.get(position).getValor()));
}
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

