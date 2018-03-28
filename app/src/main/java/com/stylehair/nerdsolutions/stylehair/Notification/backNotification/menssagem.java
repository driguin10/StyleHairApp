package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

/**
 * Created by Rodrigo on 22/03/2018.
 */

public class menssagem {
    String _id;
    String titulo;
    String texto;
    String visualizacao;
    String hora;
    String nome_salao;

    public menssagem(String _id, String titulo, String texto, String visualizacao, String hora, String nome_salao) {
        this._id = _id;
        this.titulo = titulo;
        this.texto = texto;
        this.visualizacao = visualizacao;
        this.hora = hora;
        this.nome_salao = nome_salao;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getVisualizacao() {
        return visualizacao;
    }

    public void setVisualizacao(String visualizacao) {
        this.visualizacao = visualizacao;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNome_salao() {
        return nome_salao;
    }

    public void setNome_salao(String nome_salao) {
        this.nome_salao = nome_salao;
    }
}
