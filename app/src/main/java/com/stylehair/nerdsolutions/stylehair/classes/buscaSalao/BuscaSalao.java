package com.stylehair.nerdsolutions.stylehair.classes.buscaSalao;

public class BuscaSalao {
    private int idSalao;
    private String topicoNotificacao;
    private String nome;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String linkImagem;
    private int agendamento;
    private double latitude;
    private double longitude;
    private int status;
    private int pontos;
    private int idFavorito;
    private Float distancia;

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getTopicoNotificacao() {
        return topicoNotificacao;
    }

    public void setTopicoNotificacao(String topicoNotificacao) {
        this.topicoNotificacao = topicoNotificacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public int getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(int agendamento) {
        this.agendamento = agendamento;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        status = status;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }
}
