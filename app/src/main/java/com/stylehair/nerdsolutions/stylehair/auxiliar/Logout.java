package com.stylehair.nerdsolutions.stylehair.auxiliar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.favorito.Adaptador_favorito;
import com.stylehair.nerdsolutions.stylehair.telas.login.logar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Logout {

    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    int idLogin;
    TopicoNotificacao topicoNotificacao;
    public void deslogar(Activity activity,boolean principal){
        topicoNotificacao = new TopicoNotificacao();
        SharedPreferences getSharedPreferencesL = PreferenceManager
                .getDefaultSharedPreferences(activity);

        String topic = getSharedPreferencesL.getString("topicoNotificacao","");
        idLogin = getSharedPreferencesL.getInt("idLogin",-1);
        SharedPreferences.Editor e = getSharedPreferencesL.edit();

        e.remove("logado");
        e.remove("idLogin");
        e.remove("email");
        e.remove("idUsuario");
        e.remove("nomeUser");
        e.remove("linkImagem");
        e.remove("typeUserApp");
        e.remove("idSalao");
        e.remove("idFuncionario");
        e.remove("idUserAgendamento");
        e.putBoolean("firstStart",false);
        e.apply();
        e.commit();

       /* if(!topic.equals("")) {
            topicoNotificacao.removeTopico(topic);
        }*/

        getFavoritos(String.valueOf(idLogin));

        Intent intent = new Intent(activity,logar.class);
        if(!principal)
            ActivityCompat.finishAffinity(activity);
        activity.startActivity(intent);
        if(principal)
            activity.finish();
    }

    public void getFavoritos(final String id)
    {

        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<favorito_usuario>> callBuscaFavorito = iApi.BuscaFavoritoUsuario(id);
        callBuscaFavorito.enqueue(new Callback<List<favorito_usuario>>() {
            @Override
            public void onResponse(Call<List<favorito_usuario>> call, Response<List<favorito_usuario>> response) {
                qtTentativaRealizada = 0 ;
                callBuscaFavorito.cancel();

                switch (response.code())
                {
                    case 200:
                        List<favorito_usuario> ListaFavorito = response.body();
                        if(ListaFavorito.size()>0)
                        {
                            for (favorito_usuario fav : ListaFavorito) {
                                topicoNotificacao.removeTopico(fav.getTopicoNotificacao());
                            }
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<favorito_usuario>> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    getFavoritos(id);
                }

            }
        });

    }
}
