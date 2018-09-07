package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes;


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
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderAvaliacoes extends ViewHolder implements View.OnClickListener  {
    TextView comentario;
    TextView data;
    TextView pontos;
    ImageButton excluir;
    Context contexto;
    List<AvaliacaoSalao> ListaAvaliacao;
    AvaliacaoSalao avaliacaoSalao;
    RecyclerView lista;
    int qtTentativas = 3;
    int qtTentativaRealizada = 0;
    TextView txtPontos;
    TextView txtComentarios ;

    public viewHolderAvaliacoes(View itemView, List<AvaliacaoSalao> dados) {
        super(itemView);
        comentario = (TextView) itemView.findViewById(R.id.txtComentario);
        pontos = (TextView) itemView.findViewById(R.id.txtQtpontos);
        data = (TextView) itemView.findViewById(R.id.txtDataComentario);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_comentario);
        excluir.setOnClickListener(this);
        ListaAvaliacao = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        final int position = getAdapterPosition();
        avaliacaoSalao = ListaAvaliacao.get(position);
            new AlertDialog.Builder(contexto)
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir esta avaliação? " +
                            "* Você perderá os PONTOS correspondentes a está avaliação.")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            excluiComentario(String.valueOf(avaliacaoSalao.getIdAvaliacao()),position);
                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
    }



    public void excluiComentario( final String id, final int posicao)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<ResponseBody> callExcluiServico = iApi.ExcluirAvaliacao(id);
        callExcluiServico.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                qtTentativaRealizada = 0;
                switch (response.code())
                {
                    case 204:
                        int pont = Integer.valueOf(txtPontos.getText().toString());
                        txtPontos.setText(String.valueOf(pont - ListaAvaliacao.get(posicao).getPontos()));
                        Toast.makeText(contexto,"Apagado com sucesso!!",Toast.LENGTH_LONG).show();
                        ListaAvaliacao.remove(posicao);
                        lista.setAdapter(new Adaptador_avaliacoes(ListaAvaliacao,txtPontos,txtComentarios,lista));
                        txtComentarios.setText(String.valueOf(ListaAvaliacao.size()));
                        break;

                    case 400:
                        switch (response.message())
                        {
                            case "02":
                                Toast.makeText(contexto,"Parametros incorretos!!",Toast.LENGTH_LONG).show();
                                break;

                            case "05":
                                Toast.makeText(contexto,"Erro ao excluir!!",Toast.LENGTH_LONG).show();
                                break;
                        }
                        break;
                }
                callExcluiServico.cancel();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (qtTentativaRealizada < qtTentativas) {
                    qtTentativaRealizada++;
                    excluiComentario(String.valueOf(avaliacaoSalao.getIdAvaliacao()),posicao);
                }
            }
        });
    }
}
