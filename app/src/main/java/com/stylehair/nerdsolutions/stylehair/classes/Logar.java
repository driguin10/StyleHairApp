package com.stylehair.nerdsolutions.stylehair.classes;

import java.util.List;

/**
 * Created by Rodrigo on 28/01/2018.
 */

public class Logar {
    public List<Login> login;
    public String idUser;
    public String nomeUser;
    public String linkImagem;

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
}
