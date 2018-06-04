package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.ver_notificacao;

import java.util.List;

/**
 * Created by dherrera on 15/03/2017.
 */

public class viewHolderNotification extends ViewHolder implements View.OnClickListener  {

    ImageButton excluir;
    ImageView imagem;
    TextView titulo;
    TextView nomeSalao;
    CardView card;
    TextView hora;
    Resources resources;
Context contexto;
    menssagem objeto;

    RecyclerView Lista;
    List<menssagem> ListaObjeto;

    int idLogin;

    public viewHolderNotification(View itemView, List<menssagem> datos) {
        super(itemView);

        imagem = (ImageView) itemView.findViewById(R.id.img_notification);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_notification);
        titulo = (TextView) itemView.findViewById(R.id.titulo_notification);
        nomeSalao = (TextView) itemView.findViewById(R.id.nome_salao_notification);
        hora = (TextView) itemView.findViewById(R.id.hora_notification);
        ListaObjeto = datos;
        card = (CardView) itemView.findViewById(R.id.cardst);
        card.setOnClickListener(this);
        excluir.setOnClickListener(this);
        resources = itemView.getContext().getResources();
        contexto = itemView.getContext();
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(itemView.getContext());

        idLogin = getSharedPreferences.getInt("idLogin",-1);


    }

    @Override
    public void onClick(final View view) {
        final int position = getAdapterPosition();
         objeto = ListaObjeto.get(position);

        if (view.getId() == excluir.getId()) {

            new AlertDialog.Builder(view.getContext())
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir esta notificação?")
                    .setIcon(R.drawable.icone_delete)
                    .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            BancoNotifyController crud = new BancoNotifyController(view.getContext());
                            crud.deletaRegistro(Integer.valueOf(objeto.get_id()));

                            List<menssagem> ListaObjetoAux = ListaObjeto;
                            ListaObjetoAux.remove(position);
                            Lista.setAdapter(new Adaptador_notify(ListaObjetoAux,Lista));
                        }
                    })
                    .setNegativeButton("não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //não exclui, apenas fecha a mensagem
                            dialog.dismiss();
                        }
                    })
                    .show();

        } else
        if(view.getId() == card.getId())
        {
            Intent intent = new Intent(view.getContext(),ver_notificacao.class);
            intent.putExtra("id_notificacao",objeto.get_id());
            intent.putExtra("titulo_notificacao",objeto.getTitulo());
            intent.putExtra("texto_notificacao",objeto.getTexto());
            intent.putExtra("visualizacao_notificacao",objeto.getVisualizacao());
            intent.putExtra("hora_notificacao",objeto.getHora());
            intent.putExtra("nome_salao_notificacao",objeto.getNome_salao());
            intent.putExtra("idLogin",idLogin);
            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).finish();


        }
    }




}
