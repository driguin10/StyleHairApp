package com.stylehair.nerdsolutions.stylehair.classes.buscaSalao;

import com.stylehair.nerdsolutions.stylehair.classes.Salao;
import com.stylehair.nerdsolutions.stylehair.classes.ServicoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.FuncionarioVerSalao;

import java.util.List;

public class VerSalao {




    private List<Salao> salao;
    private List<Usuario> gerente;
    private List<FuncionarioVerSalao> funcionarios;
    private List<ServicoSalao> servicos;

    public List<Salao> getSalao() {
        return salao;
    }

    public void setSalao(List<Salao> salao) {
        this.salao = salao;
    }


    public List<Usuario> getGerente() {
        return gerente;
    }

    public void setGerente(List<Usuario> gerente) {
        this.gerente = gerente;
    }

    public List<FuncionarioVerSalao> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<FuncionarioVerSalao> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<ServicoSalao> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoSalao> servicos) {
        this.servicos = servicos;
    }
}
