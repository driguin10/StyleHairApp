package com.stylehair.nerdsolutions.stylehair.Notification;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.Adaptador_notify;
import com.stylehair.nerdsolutions.stylehair.Notification.backNotification.menssagem;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.classes.Notificacoes;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class viewHolderNotificacao extends ViewHolder implements View.OnClickListener  {
    ImageButton excluir;
    ImageView imagem;
    TextView titulo;
    TextView nomeSalao;
    LinearLayout card;
    TextView hora;
    Resources resources;
    Context contexto;
    Notificacoes objeto;
    RecyclerView Lista;
    List<Notificacoes> ListaObjeto;
    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor e;
    int idLogin;
int qtNotificacao ;
    public viewHolderNotificacao(View itemView, List<Notificacoes> datos) {
        super(itemView);

        imagem = (ImageView) itemView.findViewById(R.id.img_notification);
        excluir = (ImageButton) itemView.findViewById(R.id.bt_excluir_notification);
        titulo = (TextView) itemView.findViewById(R.id.titulo_notification);
        nomeSalao = (TextView) itemView.findViewById(R.id.nome_salao_notification);
        hora = (TextView) itemView.findViewById(R.id.hora_notification);
        ListaObjeto = datos;
        card = (LinearLayout) itemView.findViewById(R.id.cardst);
        card.setOnClickListener(this);
        excluir.setOnClickListener(this);
        resources = itemView.getContext().getResources();
        contexto = itemView.getContext();
         getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(itemView.getContext());
        e = getSharedPreferences.edit();
        idLogin = getSharedPreferences.getInt("idLogin",-1);
        qtNotificacao = getSharedPreferences.getInt("qtNotificacao", 0);
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
                            IApi iApi = IApi.retrofit.create(IApi.class);
                            final Call<ResponseBody> callExcluiNotificacao = iApi.ExcluirNotificacao(objeto.getIdNotificacao());
                            callExcluiNotificacao.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    switch (response.code()) {
                                        case 204:
                                           /*if(qtNotificacao>=1)
                                           {
                                               qtNotificacao--;
                                               e.putInt("qtNotificacao",qtNotificacao);
                                               e.apply();
                                               e.commit();
                                           }*/
                                            Toast.makeText(contexto,"Excluido",Toast.LENGTH_SHORT).show();
                                            ListaObjeto.remove(position);
                                            Lista.getAdapter().notifyDataSetChanged();
                                            break;
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
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
            intent.putExtra("id_notificacao",String.valueOf(objeto.getIdNotificacao()));
            intent.putExtra("titulo_notificacao",objeto.getTitulo());
            intent.putExtra("texto_notificacao",objeto.getCorpo());
            intent.putExtra("visualizacao_notificacao",objeto.getVisualizado());
            intent.putExtra("hora_notificacao",objeto.getData());
            intent.putExtra("nome_salao_notificacao",objeto.getNomeSalao());
            intent.putExtra("idLogin",idLogin);
            ((Activity)view.getContext()).startActivityForResult(intent,1);
            ((Activity)view.getContext()).finish();
        }
    }
}
