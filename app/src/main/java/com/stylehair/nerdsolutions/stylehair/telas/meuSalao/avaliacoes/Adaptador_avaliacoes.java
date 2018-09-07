package com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.UsuarioFuncionario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.funcionario.viewHolderFuncionario;

import java.util.List;


public class Adaptador_avaliacoes extends RecyclerView.Adapter<viewHolderAvaliacoes> {
    List<AvaliacaoSalao> ListaAvaliacao;
    int qtPontos = 0;
    int qtComentarios = 0;
    TextView txtPontos;
    TextView txtComentarios ;
    RecyclerView lista;
    public Adaptador_avaliacoes(List<AvaliacaoSalao> avaliacaoSalao, TextView text_Pontos,TextView text_Comentarios,RecyclerView Rlista) {
        ListaAvaliacao = avaliacaoSalao;
        txtPontos = text_Pontos;
        txtComentarios = text_Comentarios;
        lista = Rlista;
    }


    @Override
    public viewHolderAvaliacoes onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_avaliacao, parent, false);
        return new viewHolderAvaliacoes(vista,ListaAvaliacao);
    }

    @Override
    public void onBindViewHolder(viewHolderAvaliacoes holder, int position) {
        holder.comentario.setText(ListaAvaliacao.get(position).getComentario());
        String[] data = ListaAvaliacao.get(position).getData().split("-");
        holder.data.setText(data[2]+"/"+data[1]+"/"+data[0]);
         qtPontos = qtPontos + ListaAvaliacao.get(position).getPontos();
         txtPontos.setText(String.valueOf(qtPontos));
         qtComentarios = qtComentarios + 1;
         txtComentarios.setText(String.valueOf(qtComentarios));
        holder.lista = lista;
        holder.txtComentarios = txtComentarios;
        holder.txtPontos = txtPontos;
        holder.pontos.setText(String.valueOf(ListaAvaliacao.get(position).getPontos()) + "pts");
    }
    @Override
    public int getItemCount() {
        return ListaAvaliacao.size();
    }
}

