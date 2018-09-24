package com.stylehair.nerdsolutions.stylehair.Notification;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.Notificacoes;

import java.util.List;

public class adapter_notificacao extends RecyclerView.Adapter<viewHolderNotificacao> {
    List<Notificacoes> ListaObjeto;
    RecyclerView Lista;

    public adapter_notificacao(List<Notificacoes> listaObjeto,RecyclerView lista) {
        ListaObjeto = listaObjeto;
        Lista = lista;
    }

    @Override
    public viewHolderNotificacao onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.estilo_notificacao, parent, false);
        return new viewHolderNotificacao(vista,ListaObjeto);
    }

    @Override
    public void onBindViewHolder(viewHolderNotificacao holder, int position) {
        holder.Lista = Lista;
        holder.titulo.setText(ListaObjeto.get(position).getTitulo());
        holder.nomeSalao.setText(ListaObjeto.get(position).getNomeSalao());
        holder.hora.setText(ListaObjeto.get(position).getData());
        Resources r = holder.resources;
        String titulo = ListaObjeto.get(position).getTitulo();
        int visualizado = ListaObjeto.get(position).getVisualizado();


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

        if(visualizado == 0)
        {
            holder.card.setBackgroundColor(r.getColor(R.color.corNotificacao0));
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

