package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.BuscaSalao;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_BuscaSalao extends RecyclerView.Adapter<viewHolderBuscaSalao> {

    List<BuscaSalao> ListaObjeto;


    public Adaptador_BuscaSalao(List<BuscaSalao> listaObjeto) {
        ListaObjeto = listaObjeto;
    }

    @Override
    public viewHolderBuscaSalao onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_busca, parent, false);
        return new viewHolderBuscaSalao(vista,ListaObjeto);
    }

    @Override
    public void onBindViewHolder(viewHolderBuscaSalao holder, int position) {
         Resources r = holder.resources;
         holder.nomeSalao.setText(ListaObjeto.get(position).getNome());
         holder.endereco.setText(ListaObjeto.get(position).getEndereco());
         holder.distancia.setText(String.valueOf(ListaObjeto.get(position).getDistancia()));
         Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaObjeto.get(position).getLinkImagem()).into(holder.imagem);

         if(ListaObjeto.get(position).getStatus() == 1)
         {
             holder.cardStatus.setCardBackgroundColor(r.getColor(R.color.corAberto));
             holder.status.setText("ABERTO");
         }
         else{
             holder.cardStatus.setCardBackgroundColor(r.getColor(R.color.corFechado));
             holder.status.setText("FECHADO");
         }

         holder.estrela.setRating(ListaObjeto.get(position).getEstrela());

}





    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

}

