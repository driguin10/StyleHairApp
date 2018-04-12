package com.stylehair.nerdsolutions.stylehair.classes;




public class ServicoSalao {
    private int idServicoSalao;
    private int idSalao;
    private String tipo;
    private String sexo;
    protected float valor;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
}
