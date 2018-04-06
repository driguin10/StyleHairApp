package com.stylehair.nerdsolutions.stylehair.classes;

public class UsuarioFuncionario {

    public int idUsuario;
    public String nome;
    public String linkImagem;
    public int idFuncionario;

    public UsuarioFuncionario(int idUsuario, String nome, String linkImagem, int idFuncionario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.linkImagem = linkImagem;
        this.idFuncionario = idFuncionario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
