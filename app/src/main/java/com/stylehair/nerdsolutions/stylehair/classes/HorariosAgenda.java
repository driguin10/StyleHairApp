package com.stylehair.nerdsolutions.stylehair.classes;

import java.util.ArrayList;

public class HorariosAgenda {



    private String tempoServico;
    private String almocoIni;
    private String almocoFim;
    private ArrayList<String> horarios;
    private String intervalo;

    public String getTempoServico() {
        return tempoServico;
    }

    public void setTempoServico(String tempoServico) {
        this.tempoServico = tempoServico;
    }

    public String getAlmocoIni() {
        return almocoIni;
    }

    public void setAlmocoIni(String almocoIni) {
        this.almocoIni = almocoIni;
    }

    public String getAlmocoFim() {
        return almocoFim;
    }

    public void setAlmocoFim(String almocoFim) {
        this.almocoFim = almocoFim;
    }

    public ArrayList<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<String> horarios) {
        this.horarios = horarios;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }
}
