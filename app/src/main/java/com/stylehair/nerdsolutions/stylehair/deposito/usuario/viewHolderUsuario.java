package com.stylehair.nerdsolutions.stylehair.deposito.usuario;

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
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.Notification.ver_notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.classes.Funcionario;
import com.stylehair.nerdsolutions.stylehair.classes.Usuario;

import java.util.List;


public class viewHolderUsuario extends ViewHolder implements View.OnClickListener  {
    ImageView imagemFunc;
    TextView NomeFuncionario;
    CardView card;
    Context contexto;
    List<Usuario> ListaUsuario;
    Usuario usuario;

    public viewHolderUsuario(View itemView, List<Usuario> dados) {
        super(itemView);
        imagemFunc = (ImageView) itemView.findViewById(R.id.img_funcionario);
        NomeFuncionario = (TextView) itemView.findViewById(R.id.nome_funcionario);
        card = (CardView) itemView.findViewById(R.id.cardsFunc);
        card.setOnClickListener(this);
        ListaUsuario = dados;
        contexto = itemView.getContext();
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        usuario = ListaUsuario.get(position);
    }
}
