package com.stylehair.nerdsolutions.stylehair.telas.agendamento;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderServicoFuncionarioEscolhido;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_agenda_horarios extends RecyclerView.Adapter<viewHolderServicoAgendaHorarios> {

    ArrayList<String> ListaHorario;
    RecyclerView lista;
    String Tempo;
    String Intervalo;
    TextView txtHoraEscolhido;
    Button Prosseguir;


    String ListaServicos;
    String IdFuncionario;
    String IdSalao;
    ArrayList<String> vetAux = new ArrayList<>();

    public Adaptador_agenda_horarios(ArrayList<String> listaHorario, RecyclerView listaa,String tempo,String intervalo,TextView txtHora_Escolhido, Button prosseguir, String listaServicos, String idFuncionario,  String idSalao) {
        ListaHorario = listaHorario;
        lista = listaa;
        Tempo = tempo;
        Intervalo = intervalo;
        txtHoraEscolhido = txtHora_Escolhido;
        Prosseguir = prosseguir;

        ListaServicos= listaServicos;
        IdFuncionario=idFuncionario;
        IdSalao=idSalao;
    }


    @Override
    public viewHolderServicoAgendaHorarios onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_horario_agenda, parent, false);
        return new viewHolderServicoAgendaHorarios(vista,ListaHorario,Prosseguir,vetAux);
    }

    @Override
    public void onBindViewHolder(viewHolderServicoAgendaHorarios holder, int position) {
         holder.hora.setText(ListaHorario.get(position).substring(0,5));
         holder.lista = lista;
         holder.tempo = Tempo;
         holder.intervalo = Intervalo;
         holder.txtHoraEscolhido = txtHoraEscolhido;


         holder.listaServicos = ListaServicos;
         holder.idFuncionario = IdFuncionario;
         holder.idSalao = IdSalao;

        LocalTime penultimoH =  LocalTime.parse(ListaHorario.get(ListaHorario.size()-1));
        LocalTime HoraIntervalo = LocalTime.parse(Intervalo);
       holder.ultimo = penultimoH.plusHours(HoraIntervalo.getHourOfDay())
                .plusMinutes(HoraIntervalo.getMinuteOfHour());
}




    @Override
    public int getItemCount() {
        return ListaHorario.size();
    }

}

