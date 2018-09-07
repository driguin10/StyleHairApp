package com.stylehair.nerdsolutions.stylehair.telas.agendamento.horarios;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.telas.agendamento.confirmar.confirma_agendamento;

import org.joda.time.Hours;
import org.joda.time.LocalTime;

import java.util.ArrayList;

public class viewHolderServicoAgendaHorarios extends ViewHolder implements View.OnClickListener  {
    TextView hora;
    CardView card;
    Context contexto;
    ArrayList<String> ListaHorario;
    RecyclerView lista;
    String tempo;
    String intervalo;
    String AlmocoIni;
    String AlmocoFim;
    LocalTime ultimo;
    TextView txtHoraEscolhido;
    Button Prosseguir;
    String idServicos;
    String idFuncionario;
    String idSalao;
    ArrayList<String> vetAux;
    ArrayList<String> ServicosLista;
    String Data;
    String NomeFuncionario;
    String Imagemfuncionario;
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
            LocalTime HoraAlmocoIni = new LocalTime();
            if(AlmocoIni !=null){
                 HoraAlmocoIni = LocalTime.parse(AlmocoIni);
            }

            //LocalTime HoraAlmocoFim = LocalTime.parse(AlmocoFim);
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

                Boolean flagHorarioPertoAlmoco = false;
                if(AlmocoIni != null) {
                    if (proximaTempo.compareTo(HoraAlmocoIni) == -1 || proximaTempo.compareTo(HoraAlmocoIni) == 0 && proximaTempo.equals(HoraAlmocoIni)) {
                        flagHorarioPertoAlmoco = true;
                    }
                }

                if (soma.toString().equals(LocalTime.parse(ListaHorarioAux.get(x)).toString())) {
                    if (LocalTime.parse(ListaHorarioAux.get(x)).toString().equals(proximaTempo.toString()) || flagHorarioPertoAlmoco) {
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
                LocalTime HoraInicioServico = LocalTime.parse(vetAux.get(0));

                //somar horarios do servicolista e somar a horainicio para dar o tempo de serviço
                LocalTime horaFimAux = HoraInicioServico;
                for(int x=0;x<ServicosLista.size();x++)
                {
                    LocalTime Hservico = LocalTime.parse(ServicosLista.get(x).split("#")[3]);
                    horaFimAux = horaFimAux.plusHours(Hservico.getHourOfDay())
                            .plusMinutes(Hservico.getMinuteOfHour());
                }

                Intent intent = new Intent(contexto,confirma_agendamento.class);
                intent.putExtra("idSalao",idSalao);
                intent.putExtra("idFuncionario",idFuncionario);
                intent.putExtra("idServicos",idServicos);
                intent.putStringArrayListExtra("listaServicos",ServicosLista);// lista dos serviços que vai ser prestado
                intent.putExtra("horaIni",HoraInicioServico.toString().substring(0,5));
                intent.putExtra("horaFim",horaFimAux.toString().substring(0,5));
                intent.putExtra("data",Data);
                intent.putExtra("nomeFuncionario",NomeFuncionario);
                intent.putExtra("imagemFuncionario",Imagemfuncionario);
                contexto.startActivity(intent);
            }
    }


}

