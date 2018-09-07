package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.classes.TipoUsuario;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//atualiza informações da tele principal
public class AtualizaInfos {
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    SharedPreferences getSharedPreferences;
    Context context;
    boolean telaPrincipal;

    public AtualizaInfos(Context context) {
        this.context = context;
    }

    public void atualizatipo(boolean tela_Principal){
        telaPrincipal = tela_Principal;
        getSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int idLogin = getSharedPreferences.getInt("idLogin",-1);
        busca(idLogin);
    }

    private void busca(final int id)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<TipoUsuario> callTipos = iApi.tipoUsuario(id);
        callTipos.enqueue(new Callback<TipoUsuario>() {
            @Override
            public void onResponse(Call<TipoUsuario> call, Response<TipoUsuario> response) {
                callTipos.cancel();
                qtTentativaRealizada = 0;
                SharedPreferences.Editor e = getSharedPreferences.edit();

                if(response.isSuccessful()) {
                    TipoUsuario tipo = response.body();

                    e.putString("typeUserApp","");
                    e.putString("idSalao","-1");
                    e.putString("idFuncionario","-1");
                    e.putString("idUsuario","-1");

                    e.putString("nomeUser",tipo.getNomeUsuario());
                    e.putString("linkImagem",tipo.getLinkImagem());
                    if(!tipo.getTopicoNotificacao().equals("")) {
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.addTopico(tipo.getTopicoNotificacao());
                    }

                    e.apply();
                    e.commit();

                    int id_suario = -1;
                    int id_funcionario = -1;
                    int id_salao = -1;
                    int id_salao_funcionario = -1;

                    if(tipo.getIdSalao()>=0) {
                        id_salao = tipo.getIdSalao();
                    }

                    if(tipo.getIdFuncionario()>=0) {
                        id_funcionario = tipo.getIdFuncionario();
                        id_salao_funcionario = tipo.getIdSalaoFuncionario();
                    }

                    if(tipo.getIdUsuario()>=0) {
                        id_suario = tipo.getIdUsuario();
                    }

                    if( id_salao > -1)//gerente
                    {
                        e.putString("typeUserApp","GERENTE");
                        e.putString("idSalao",String.valueOf(id_salao));
                        e.putString("idFuncionario",String.valueOf(id_funcionario));
                        e.putString("idUsuario",String.valueOf(tipo.getIdUsuario()));
                    }
                    else
                    if(id_funcionario > -1)//funcionario
                    {
                        e.putString("typeUserApp","FUNCIONARIO");
                        e.putString("idFuncionario",String.valueOf(id_funcionario));
                        e.putString("idUsuario",String.valueOf(tipo.getIdUsuario()));
                        e.putString("idSalao",String.valueOf(id_salao_funcionario));
                    }
                    else
                    if(id_suario > -1)
                    {
                        e.putString("typeUserApp","COMUMUSER");
                        e.putString("idUsuario",String.valueOf(tipo.getIdUsuario()));
                    }
                    else
                    {
                        e.putString("typeUserApp","COMUM");
                    }

                    e.apply();
                    e.commit();

                    if(telaPrincipal)
                    ((principal)context).atualizaTela();
                }
            }

            @Override
            public void onFailure(Call<TipoUsuario> call, Throwable t) {
                if(qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    busca(id);
                }
            }
        });
    }
}



