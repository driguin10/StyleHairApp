package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;



public class Adaptador_servico_funcionario_escolhido extends RecyclerView.Adapter<viewHolderServicoFuncionarioEscolhido> {

    List<ServicoSalao> ListaServicoSalao;
    String idFunc;
    RecyclerView lista;



    int selected_position = 0;


    public Adaptador_servico_funcionario_escolhido(List<ServicoSalao> listaServicoSalao, String idFuncionario,RecyclerView listaa) {
        ListaServicoSalao = listaServicoSalao;
        idFunc = idFuncionario;
        lista = listaa;
    }


    @Override
    public viewHolderServicoFuncionarioEscolhido onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico_escolha, parent, false);
        return new viewHolderServicoFuncionarioEscolhido(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoFuncionarioEscolhido holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.idF = idFunc;
         holder.lista = lista;


}




    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

