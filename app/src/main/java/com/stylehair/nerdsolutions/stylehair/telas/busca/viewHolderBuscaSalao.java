package com.stylehair.nerdsolutions.stylehair.telas.busca;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.buscaSalao.BuscaSalao;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class viewHolderBuscaSalao extends ViewHolder implements View.OnClickListener  {

    CardView card;
    CircleImageView imagem;
    TextView nomeSalao;
    TextView pontos;
    TextView endereco;
    CardView bt_favorito;
    ImageView coracao;

    TextView distancia;
    CardView cardStatus;
    TextView status;

    Resources resources;
    Context contexto;
    BuscaSalao objeto;
    List<BuscaSalao> ListaObjeto;



    public viewHolderBuscaSalao(View itemView, List<BuscaSalao> datos) {
        super(itemView);

        imagem = (CircleImageView) itemView.findViewById(R.id.img_salao);
        nomeSalao = (TextView) itemView.findViewById(R.id.nome_salao);
        pontos = (TextView) itemView.findViewById(R.id.txtqt_pontos);
        endereco = (TextView) itemView.findViewById(R.id.endereco);

        bt_favorito = (CardView) itemView.findViewById(R.id.card_btFavorito);
        coracao = (ImageView) itemView.findViewById(R.id.coracao);

        distancia = (TextView) itemView.findViewById(R.id.distancia);


        cardStatus = (CardView) itemView.findViewById(R.id.cardStatus);
        status = (TextView) itemView.findViewById(R.id.status);

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

            Toast.makeText(view.getContext(),"favorito pos -" + String.valueOf(position),Toast.LENGTH_LONG).show();
        } else
        if(view.getId() == card.getId())
        {
            Intent intent = new Intent(view.getContext(),verSalao_buscado.class);
            intent.putExtra("idSalao",String.valueOf(objeto.getIdSalao()));
            view.getContext().startActivity(intent);

        }
    }




}
