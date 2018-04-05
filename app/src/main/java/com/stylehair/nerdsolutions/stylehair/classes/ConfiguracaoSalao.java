package com.stylehair.nerdsolutions.stylehair.classes;

import java.util.List;

public class ConfiguracaoSalao {
    public List<ConfigSalao> configSalao;
    public List<ConfigHorarioSalao> configHorarioSalao;

    public List<ConfigSalao> getConfigSalao() {
        return configSalao;
    }

    public void setConfigSalao(List<ConfigSalao> configSalao) {
        this.configSalao = configSalao;
    }

    public List<ConfigHorarioSalao> getConfigHorarioSalao() {
        return configHorarioSalao;
    }

    public void setConfigHorarioSalao(List<ConfigHorarioSalao> configHorarioSalao) {
        this.configHorarioSalao = configHorarioSalao;
    }
}
