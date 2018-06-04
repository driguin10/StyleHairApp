package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.R;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class Adaptador_notify extends RecyclerView.Adapter<viewHolderNotification> {

    List<menssagem> ListaObjeto;
    RecyclerView Lista;


    public Adaptador_notify(List<menssagem> listaObjeto,RecyclerView lista) {
        ListaObjeto = listaObjeto;
        Lista = lista;
    }

    @Override
    public viewHolderNotification onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_notificacao, parent, false);

        return new viewHolderNotification(vista,ListaObjeto);
    }

    @Override
    public void onBindViewHolder(viewHolderNotification holder, int position) {
         holder.Lista = Lista;
         holder.titulo.setText(ListaObjeto.get(position).getTitulo());
         holder.nomeSalao.setText(ListaObjeto.get(position).getNome_salao());
         holder.hora.setText(ListaObjeto.get(position).getHora());
         Resources r = holder.resources;

            String titulo = ListaObjeto.get(position).getTitulo();
            String visualizado = ListaObjeto.get(position).getVisualizacao();


        if(verifica(titulo).equals("menssagem"))
        {
            holder.imagem.setImageDrawable(r.getDrawable(R.drawable.icone_menssagem));
        }
        else
        if(verifica(titulo).equals("promocao"))
        {
            holder.imagem.setImageDrawable(r.getDrawable(R.drawable.icone_promocao));
        }
        else
        if(verifica(titulo).equals("atencao"))
        {
            holder.imagem.setImageDrawable(r.getDrawable(R.drawable.icone_atencao));
        }

        if(visualizado.equals("0"))
        {

            holder.card.setCardBackgroundColor(r.getColor(R.color.corNotificacao0));

        }
}


public String verifica(String texto)
{
    String imagem ="menssagem";


    String[] titulosPromocionais ={"promoção","corra","imperdivel"};
    String[] titulosAtencao ={"atenção","alerta"};

    for(int i=0; i<titulosPromocionais.length;i++)
    {
        if(texto.toLowerCase().contains(titulosPromocionais[i]))
            imagem = "promocao";
    }

    for(int i=0; i<titulosAtencao.length;i++)
    {
        if(texto.toLowerCase().contains(titulosAtencao[i]))
            imagem = "atencao";

    }

    return imagem;

}



    @Override
    public int getItemCount() {
        return ListaObjeto.size();
    }

}

