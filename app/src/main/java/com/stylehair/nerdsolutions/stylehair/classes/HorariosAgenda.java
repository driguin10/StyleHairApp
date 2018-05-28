package com.stylehair.nerdsolutions.stylehair.classes;

import java.util.ArrayList;

public class HorariosAgenda {



    private String tempoServico;
    private ArrayList<String> horarios;

    public String getTempoServico() {
        return tempoServico;
    }

    public void setTempoServico(String tempoServico) {
        this.tempoServico = tempoServico;
    }

    public ArrayList<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<String> horarios) {
        this.horarios = horarios;
    }
}
