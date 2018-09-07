package com.stylehair.nerdsolutions.stylehair.classes;

public class UsuarioFuncionarioBusca {
    public int idFuncionario;
    public int idUsuario;
    public String nome;
    public String linkImagem;
    public String telefone;
    public int idServico;
    public String servico;
    public String[] listaAux;
    boolean selected;

    public UsuarioFuncionarioBusca(int idFuncionario, int idUsuario, String nome, String linkImagem, String telefone, int idServico, String servico) {
        this.idFuncionario = idFuncionario;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.linkImagem = linkImagem;
        this.telefone = telefone;
        this.idServico = idServico;
        this.servico = servico;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
