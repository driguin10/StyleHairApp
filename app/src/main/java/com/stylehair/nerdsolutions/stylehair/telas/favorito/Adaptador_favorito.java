package com.stylehair.nerdsolutions.stylehair.telas.favorito;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.AvaliacaoSalao;
import com.stylehair.nerdsolutions.stylehair.classes.favorito_usuario;
import com.stylehair.nerdsolutions.stylehair.telas.meuSalao.avaliacoes.viewHolderAvaliacoes;

import java.util.List;


public class Adaptador_favorito extends RecyclerView.Adapter<viewHolderFavorito> {
    List<favorito_usuario> ListaFavorito;
    RecyclerView lista;

    public Adaptador_favorito(List<favorito_usuario> listaFavorito, RecyclerView Rlista) {
        ListaFavorito = listaFavorito;
        lista = Rlista;
    }


    @Override
    public viewHolderFavorito onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_favorito, parent, false);
        return new viewHolderFavorito(vista,ListaFavorito);
    }

    @Override
    public void onBindViewHolder(viewHolderFavorito holder, int position) {
        holder.lista = lista;
        holder.nome.setText(ListaFavorito.get(position).getNome());
        if(ListaFavorito.get(position).getLinkImagem()=="")
            holder.imagem.setImageDrawable(holder.itemView.getResources().getDrawable(R.drawable.img_padrao_user));
        else
            Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaFavorito.get(position).getLinkImagem()).into(holder.imagem);
}
    @Override
    public int getItemCount() {
        return ListaFavorito.size();
    }

}

