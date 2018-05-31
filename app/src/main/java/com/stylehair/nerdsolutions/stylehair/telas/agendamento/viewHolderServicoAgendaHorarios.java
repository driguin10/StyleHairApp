package com.stylehair.nerdsolutions.stylehair.telas.agendamento;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;

import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class viewHolderServicoAgendaHorarios extends ViewHolder implements View.OnClickListener  {
    TextView hora;
    CardView card;
    Context contexto;
    ArrayList<String> ListaHorario;
    RecyclerView lista;
    String tempo;
    String intervalo;
    LocalTime ultimo;
    TextView txtHoraEscolhido;
    Button Prosseguir;


    String listaServicos;
    String idFuncionario;
    String idSalao;
    ArrayList<String> vetAux;
    public viewHolderServicoAgendaHorarios(View itemView,  ArrayList<String> dados,Button prosseguir,ArrayList<String> vet) {
        super(itemView);
        hora = (TextView) itemView.findViewById(R.id.horario);
        card = (CardView) itemView.findViewById(R.id.cardsHorarioEscolhido);
        card.setOnClickListener(this);
       Prosseguir = prosseguir;
       Prosseguir.setOnClickListener(this);
        ListaHorario = dados;
        contexto = itemView.getContext();
        itemView.setOnClickListener(this);
        vetAux = vet;
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();


        if(v.getId() == card.getId())
        {
            vetAux.clear();
            ArrayList<String> ListaHorarioAux = ListaHorario;
            LocalTime HoraSelect = LocalTime.parse(ListaHorarioAux.get(position));
            LocalTime HoraIntervalo = LocalTime.parse(intervalo);
            LocalTime HoraTotalServico = LocalTime.parse(tempo);

            int ver = 0;
            for (int x = 0; x < ListaHorarioAux.size(); x++) {
                if (ListaHorarioAux.get(x).equals(ultimo.toString()))
                    ver = 1;
            }
            if (ver == 0) {
                ListaHorarioAux.add(ultimo.toString());
            }


            LocalTime proximaTempo = HoraSelect.plusHours(HoraTotalServico.getHourOfDay())
                    .plusMinutes(HoraTotalServico.getMinuteOfHour());


            vetAux.add(HoraSelect.toString());
            int posAux = 0;
            int flag = 0;
            for (int x = position + 1; x < ListaHorarioAux.size(); x++) {
                LocalTime Hvetor = LocalTime.parse(vetAux.get(posAux));
                LocalTime soma = Hvetor.plusHours(HoraIntervalo.getHourOfDay())
                        .plusMinutes(HoraIntervalo.getMinuteOfHour());
                if (soma.toString().equals(LocalTime.parse(ListaHorarioAux.get(x)).toString())) {
                    if (LocalTime.parse(ListaHorarioAux.get(x)).toString().equals(proximaTempo.toString())) {
                        vetAux.add(ListaHorarioAux.get(x));
                        flag = 1;
                        break;
                    } else {
                        vetAux.add(ListaHorarioAux.get(x));
                        posAux++;
                    }
                } else {
                    break;
                }
            }

            if (flag == 1) {
                Toast.makeText(contexto, "horario disponivel !", Toast.LENGTH_LONG).show();
                txtHoraEscolhido.setText(ListaHorarioAux.get(position).substring(0, 5));
                Prosseguir.setEnabled(true);
                Prosseguir.setAlpha(1f);
            } else {
                Toast.makeText(contexto, "O serviço não pode ser feito neste horario!", Toast.LENGTH_LONG).show();
                txtHoraEscolhido.setText("");
                Prosseguir.setEnabled(false);
                Prosseguir.setAlpha(.4f);

            }
        }
        else
            if(v.getId() == Prosseguir.getId())
            {
                Log.d("xex",listaServicos + " - " + idFuncionario + " - " + idSalao);
                Log.d("xex",vetAux.toString());
            }


    }


}

