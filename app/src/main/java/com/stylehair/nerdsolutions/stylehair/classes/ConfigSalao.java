package com.stylehair.nerdsolutions.stylehair.classes;

public class ConfigSalao {

    private int idConfiguracao;
    private int idSalao;
    private String tempoReserva;
    private String tempoMinAgenda;

    public int getIdConfiguracao() {
        return idConfiguracao;
    }

    public void setIdConfiguracao(int idConfiguracao) {
        this.idConfiguracao = idConfiguracao;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getTempoReserva() {
        return tempoReserva;
    }

    public void setTempoReserva(String tempoReserva) {
        this.tempoReserva = tempoReserva;
    }

    public String getTempoMinAgenda() {
        return tempoMinAgenda;
    }

    public void setTempoMinAgenda(String tempoMinAgenda) {
        this.tempoMinAgenda = tempoMinAgenda;
    }
}
