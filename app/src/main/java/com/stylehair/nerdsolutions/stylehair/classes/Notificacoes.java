package com.stylehair.nerdsolutions.stylehair.classes;

public class Notificacoes {
    private int idNotificacao;
    private String titulo;
    private String corpo;
    private String data;
    private int visualizado;
    private String nomeSalao;
    private int idUsuario;

    public int getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(int idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(int visualizado) {
        this.visualizado = visualizado;
    }

    public String getNomeSalao() {
        return nomeSalao;
    }

    public void setNomeSalao(String nomeSalao) {
        this.nomeSalao = nomeSalao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
