package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.ver_notificacao;
import com.stylehair.nerdsolutions.stylehair.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderBuscaSalao extends ViewHolder implements View.OnClickListener  {

    CardView card;
    CircleImageView imagem;
    TextView nomeSalao;
    RatingBar estrela;
    TextView endereco;
    CardView bt_favorito;
    ImageView coracao;

    TextView distancia;

    Resources resources;
    Context contexto;
    menssagem objeto;


    List<menssagem> ListaObjeto;



    public viewHolderBuscaSalao(View itemView, List<menssagem> datos) {
        super(itemView);

        imagem = (CircleImageView) itemView.findViewById(R.id.img_salao);
        nomeSalao = (TextView) itemView.findViewById(R.id.nome_salao);
        estrela = (RatingBar) itemView.findViewById(R.id.estrelas);
        endereco = (TextView) itemView.findViewById(R.id.endereco);

        bt_favorito = (CardView) itemView.findViewById(R.id.card_btFavorito);
        coracao = (ImageView) itemView.findViewById(R.id.coracao);

        distancia = (TextView) itemView.findViewById(R.id.distancia);

        ListaObjeto = datos;
        card = (CardView) itemView.findViewById(R.id.cardsBusca);
        card.setOnClickListener(this);
        bt_favorito.setOnClickListener(this);
        resources = itemView.getContext().getResources();
        contexto = itemView.getContext();


    }

    @Override
    public void onClick(final View view) {
        int position = getAdapterPosition();
         objeto = ListaObjeto.get(position);

        if (view.getId() == bt_favorito.getId()) {


        } else
        if(view.getId() == card.getId())
        {
            /*Intent intent = new Intent(view.getContext(),ver_notificacao.class);
            intent.putExtra("id_notificacao",objeto.get_id());
            intent.putExtra("titulo_notificacao",objeto.getTitulo());
            intent.putExtra("texto_notificacao",objeto.getTexto());
            intent.putExtra("visualizacao_notificacao",objeto.getVisualizacao());
            intent.putExtra("hora_notificacao",objeto.getHora());
            intent.putExtra("nome_salao_notificacao",objeto.getNome_salao());
            intent.putExtra("idLogin",idLogin);
            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).finish();*/
        }
    }




}
