package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.BuscaSalao;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;



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
         holder.endereco.setText(ListaObjeto.get(position).getEndereco()+","+ListaObjeto.get(position).getNumero()+","+ListaObjeto.get(position).getCidade());


        float distanc = ListaObjeto.get(position).getDistancia();
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        format.setMaximumIntegerDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);


         holder.distancia.setText(format.format(distanc)+"km");
         if(ListaObjeto.get(position).getLinkImagem().equals(""))
            holder.imagem.setImageDrawable(r.getDrawable(R.drawable.img_padrao_user));
         else
            Picasso.with(holder.contexto).load("http://stylehair.xyz/" + ListaObjeto.get(position).getLinkImagem()).into(holder.imagem);


         if(ListaObjeto.get(position).getStatus() == 1)
         {
             holder.cardStatus.setCardBackgroundColor(r.getColor(R.color.corAberto));
             holder.status.setText("ABERTO");
         }
         else
         if(ListaObjeto.get(position).getStatus() == 0){
             holder.cardStatus.setCardBackgroundColor(r.getColor(R.color.corFechado));
             holder.status.setText("FECHADO");
         }
         else
         {
             holder.cardStatus.setCardBackgroundColor(r.getColor(R.color.corAlmoco));
             holder.status.setText("ALMOÇO");
         }

        if(ListaObjeto.get(position).getIdFavorito()>=0)
        {
            holder.coracao.setImageDrawable(r.getDrawable(R.drawable.icone_favorito));

        }
        else{
            holder.coracao.setImageDrawable(r.getDrawable(R.drawable.icone_favorito_of));

        }

         holder.pontos.setText(String.valueOf(ListaObjeto.get(position).getPontos()));

}





    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

}

