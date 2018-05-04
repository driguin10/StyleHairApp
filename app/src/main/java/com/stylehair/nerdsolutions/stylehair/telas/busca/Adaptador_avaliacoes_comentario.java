package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.viewHolderAvaliacoes;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_avaliacoes_comentario extends RecyclerView.Adapter<viewHolderAvaliacoes_comentario> {

    List<AvaliacaoSalao> ListaAvaliacao;

    public  Adaptador_avaliacoes_comentario(List<AvaliacaoSalao> avaliacaoSalao) {
        ListaAvaliacao = avaliacaoSalao;
    }


    @Override
    public viewHolderAvaliacoes_comentario onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_comentario, parent, false);
        return new viewHolderAvaliacoes_comentario(vista,ListaAvaliacao);
    }

    @Override
    public void onBindViewHolder(viewHolderAvaliacoes_comentario holder, int position) {
        holder.comentario.setText(ListaAvaliacao.get(position).getComentario());
        String[] data = ListaAvaliacao.get(position).getData().split("-");
        holder.data.setText(data[2]+"/"+data[1]+"/"+data[0]);
}
    @Override
    public int getItemCount() {
        return ListaAvaliacao.size();
    }

}

