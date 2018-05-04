package com.stylehair.nerdsolutions.stylehair.telas.busca;

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
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.Adaptador_avaliacoes;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderAvaliacoes_comentario extends ViewHolder   {

    TextView comentario;
    TextView data;
    Context contexto;
    List<AvaliacaoSalao> ListaAvaliacao;
    AvaliacaoSalao avaliacaoSalao;

    public viewHolderAvaliacoes_comentario(View itemView, List<AvaliacaoSalao> dados) {
        super(itemView);
        comentario = (TextView) itemView.findViewById(R.id.txtComentario);
        data = (TextView) itemView.findViewById(R.id.txtData);
        ListaAvaliacao = dados;
        contexto = itemView.getContext();
    }

}
