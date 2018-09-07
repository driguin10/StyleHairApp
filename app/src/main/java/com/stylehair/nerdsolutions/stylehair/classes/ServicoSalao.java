package com.stylehair.nerdsolutions.stylehair.classes;

public class ServicoSalao {
    private int idServicoSalao;
    private int idSalao;
    private String servico;
    private String tempo;
    private String sexo;
    protected float valor;
    private  boolean selected;

    public int getIdServicoSalao() {
        return idServicoSalao;
    }

    public void setIdServicoSalao(int idServicoSalao) {
        this.idServicoSalao = idServicoSalao;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
