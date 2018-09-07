package com.stylehair.nerdsolutions.stylehair.classes;

import java.util.List;


public class Logar {
    public List<Login> login;
    public String idUser;
    public String nomeUser;
    public String linkImagem;
    public String topico;
    public List<favorito_usuario> favoritos;

    public List<Login> getLogin() {
        return login;
    }

    public void setLogin(List<Login> login) {
        this.login = login;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public List<favorito_usuario> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<favorito_usuario> favoritos) {
        this.favoritos = favoritos;
    }
}
