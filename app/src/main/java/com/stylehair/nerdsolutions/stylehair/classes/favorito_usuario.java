package com.stylehair.nerdsolutions.stylehair.classes;

public class favorito_usuario {
    private int idFavorito;
    private int idLogin;
    private int idSalao;
    private String linkImagem;
    private String nome;
    private String topicoNotificacao;

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public int getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(int idSalao) {
        this.idSalao = idSalao;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTopicoNotificacao() {
        return topicoNotificacao;
    }

    public void setTopicoNotificacao(String topicoNotificacao) {
        this.topicoNotificacao = topicoNotificacao;
    }
}
