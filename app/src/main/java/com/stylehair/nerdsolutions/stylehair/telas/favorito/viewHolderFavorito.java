package com.stylehair.nerdsolutions.stylehair.telas.favorito;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.auxiliar.TopicoNotificacao;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.busca.verSalao_buscado;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.Adaptador_avaliacoes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderFavorito extends ViewHolder implements View.OnClickListener  {
    ImageButton excluir;
    Context contexto;
    List<favorito_usuario> ListaFavorito;
    favorito_usuario favorito_usuario;
    RecyclerView lista;
    TextView nome;
    CircleImageView imagem;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    SharedPreferences getSharedPreferences;


    public viewHolderFavorito(View itemView,  List<favorito_usuario> dados) {
        super(itemView);
        imagem = (CircleImageView) itemView.findViewById(R.id.img_salao);
        nome = (TextView) itemView.findViewById(R.id.nome_salao);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_favorito);
        excluir.setOnClickListener(this);
        ListaFavorito = dados;
        contexto = itemView.getContext();
        getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(contexto);
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        favorito_usuario = ListaFavorito.get(position);
            new AlertDialog.Builder(contexto)
                    .setTitle("Excluir")
                    .setMessage("Deseja remover este salão do seus favoritos? ")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            excluiFavorito(String.valueOf(favorito_usuario.getIdFavorito()),position);
                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
    }



   public void excluiFavorito( final String id, final int posicao)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiFavorito = iApi.DeletarFavorito(id);
        callExcluiFavorito.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                qtTentativaRealizada = 0;
                switch (response.code())
                {
                    case 204:
                        TopicoNotificacao topicoNotificacao = new TopicoNotificacao();
                        topicoNotificacao.removeTopico(ListaFavorito.get(posicao).getTopicoNotificacao());

                        String topicos = getSharedPreferences.getString("topicosFavoritos","");
                        ArrayList<String> arrayTopFavoritos = new ArrayList<>();
                        Gson gson = new Gson();
                        if(!topicos.equals("")) {
                            arrayTopFavoritos = gson.fromJson(topicos, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            for (int x = 0; x < arrayTopFavoritos.size(); x++) {
                                if (arrayTopFavoritos.get(x).equals(ListaFavorito.get(posicao).getTopicoNotificacao())) {
                                    arrayTopFavoritos.remove(x);
                                }
                            }

                            String jsonTopicoFavoritos = gson.toJson(arrayTopFavoritos);
                            SharedPreferences.Editor e = getSharedPreferences.edit();
                            e.putString("topicosFavoritos", jsonTopicoFavoritos);
                            e.apply();
                            e.commit();
                        }

                        ListaFavorito.remove(posicao);
                        lista.setAdapter(new Adaptador_favorito(ListaFavorito,lista));
                        Toast.makeText(contexto,"Apagado com sucesso!!",Toast.LENGTH_LONG).show();



                         /*
                         remover dos favoritos
                        pegar lista do shared jogar em uma lista procurar qual topico que é igual e remover e depois salvar novamente
                         */
                        break;
                }

                callExcluiFavorito.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    excluiFavorito(id,posicao);
                }
            }
        });
    }

}
