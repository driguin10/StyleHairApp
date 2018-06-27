package com.stylehair.nerdsolutions.stylehair.classes;



/**
 * Created by Rodrigo on 19/03/2018.
 */

public class TipoUsuario {
    int idUsuario;
    int idFuncionario;
    int idSalao;
    String nomeUsuario;
    String topicoNotificacao;
    String linkImagem;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalão(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTopicoNotificacao() {
        return topicoNotificacao;
    }

    public void setTopicoNotificacao(String topicoNotificacao) {
        this.topicoNotificacao = topicoNotificacao;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }
}
