package com.stylehair.nerdsolutions.stylehair.telas.agendamento.horarios;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;

import org.joda.time.LocalTime;

import java.util.ArrayList;


public class Adaptador_agenda_horarios extends RecyclerView.Adapter<viewHolderServicoAgendaHorarios> {
    ArrayList<String> ListaHorario;
    RecyclerView lista;
    String Tempo;
    String Intervalo;
    String AlmocoIni;
    String AlmocoFim;
    TextView txtHoraEscolhido;
    Button Prosseguir;
    String IdServicos;
    String IdFuncionario;
    String IdSalao;
    ArrayList<String> vetAux = new ArrayList<>();
    ArrayList<String> ServicosLista;
    String Data;
    String NomeFuncionario;
    String Imagemfuncionario;

    public Adaptador_agenda_horarios(ArrayList<String> listaHorario, RecyclerView listaa,String tempo,String intervalo,TextView txtHora_Escolhido, Button prosseguir, ArrayList<String> servicosLista, String idFuncionario,  String idSalao,String idServicos,String data,String nomeFuncionario, String imagemfuncionario ,String almocoIni, String almocoFim) {
        ListaHorario = listaHorario;
        lista = listaa;
        Tempo = tempo;
        Intervalo = intervalo;
        txtHoraEscolhido = txtHora_Escolhido;
        Prosseguir = prosseguir;
        ServicosLista = servicosLista;
        IdFuncionario=idFuncionario;
        IdSalao=idSalao;
        IdServicos = idServicos;
        Data = data;
        NomeFuncionario = nomeFuncionario;
        Imagemfuncionario = imagemfuncionario;
        AlmocoIni =almocoIni;
        AlmocoFim =almocoFim;
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
         holder.AlmocoIni = AlmocoIni;
         holder.AlmocoFim = AlmocoFim;
         holder.intervalo = Intervalo;
         holder.txtHoraEscolhido = txtHoraEscolhido;
         holder.Data = Data;
         holder.ServicosLista = ServicosLista;
         holder.idFuncionario = IdFuncionario;
         holder.idSalao = IdSalao;
         holder.idServicos = IdServicos;
         holder.NomeFuncionario = NomeFuncionario;
         holder.Imagemfuncionario = Imagemfuncionario;
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

