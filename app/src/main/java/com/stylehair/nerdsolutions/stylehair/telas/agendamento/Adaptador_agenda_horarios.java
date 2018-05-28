package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderServicoFuncionarioEscolhido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_agenda_horarios extends RecyclerView.Adapter<viewHolderServicoAgendaHorarios> {

    ArrayList<String> ListaHorario;
    RecyclerView lista;


    public Adaptador_agenda_horarios(ArrayList<String> listaHorario, RecyclerView listaa) {
        ListaHorario = listaHorario;
        lista = listaa;

    }


    @Override
    public viewHolderServicoAgendaHorarios onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_horario_agenda, parent, false);
        return new viewHolderServicoAgendaHorarios(vista,ListaHorario);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoAgendaHorarios holder, int position) {
         holder.hora.setText(ListaHorario.get(position));
         holder.lista = lista;

}




    @Override
    public int getItemCount() {
        return ListaHorario.size();
    }

}

