package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import java.util.List;
import java.util.Locale;


public class Adaptador_servico_funcionario extends RecyclerView.Adapter<viewHolderServicoFuncionario> {
    List<ServicoSalao> ListaServicoSalao;
    String idFunc;
    View view;

    public Adaptador_servico_funcionario(List<ServicoSalao> listaServicoSalao,String idFuncionario,View v) {
        ListaServicoSalao = listaServicoSalao;
        idFunc = idFuncionario;
        view = v;
    }

    @Override
    public viewHolderServicoFuncionario onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_servico, parent, false);
        return new viewHolderServicoFuncionario(vista,ListaServicoSalao);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoFuncionario holder, int position) {
         holder.NomeServico.setText(ListaServicoSalao.get(position).getServico());
         holder.valor.setText("R$"+String.format(Locale.getDefault(),"%.2f", ListaServicoSalao.get(position).getValor()));
         holder.idF = idFunc;
         holder.view = view;
    }
    @Override
    public int getItemCount() {
        return ListaServicoSalao.size();
    }

}

