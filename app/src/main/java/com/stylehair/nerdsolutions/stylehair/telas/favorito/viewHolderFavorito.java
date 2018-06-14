package com.stylehair.nerdsolutions.stylehair.telas.favorito;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.Adaptador_avaliacoes;

import java.io.IOException;
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


    public viewHolderFavorito(View itemView,  List<favorito_usuario> dados) {
        super(itemView);
        imagem = (CircleImageView) itemView.findViewById(R.id.img_salao);
        nome = (TextView) itemView.findViewById(R.id.nome_salao);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_favorito);
        excluir.setOnClickListener(this);
        ListaFavorito = dados;
        contexto = itemView.getContext();
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
                        Toast.makeText(contexto,"Apagado com sucesso!!",Toast.LENGTH_LONG).show();
                        ListaFavorito.remove(posicao);
                        lista.setAdapter(new Adaptador_favorito(ListaFavorito,lista));
                        break;
                }

                callExcluiFavorito.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    excluiFavorito(id,posicao);
                } else {

                    if (t instanceof IOException) {
                        Log.d("xex", "this is an actual network failure timeout:( inform the user and possibly retry");
                        Log.d("xex", String.valueOf(t.getCause()));
                    } else if (t instanceof IllegalStateException) {
                        Log.d("xex", "ConversionError");
                        Log.d("xex", String.valueOf(t.getCause()));
                    } else {
                        Log.d("xex", "erro");
                        Log.d("xex", String.valueOf(t.getCause()));
                        Log.d("xex", String.valueOf(t.getLocalizedMessage()));
                    }

                }
            }
        });
    }

}
