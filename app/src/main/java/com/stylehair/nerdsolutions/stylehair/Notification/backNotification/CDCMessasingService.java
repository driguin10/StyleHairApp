package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stylehair.nerdsolutions.stylehair.Notification.adapter_notificacao;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.api.IApi;
import com.stylehair.nerdsolutions.stylehair.classes.Notificacoes;
import com.stylehair.nerdsolutions.stylehair.telas.principal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CDCMessasingService extends FirebaseMessagingService {

    int qtNotificacao;
    int idLogin;
    boolean recebeNotify;
    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor e;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification(),remoteMessage);
    }

    private void showNotification(RemoteMessage.Notification notification,RemoteMessage remoteMessage) {
        getSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
       e = getSharedPreferences.edit();

        idLogin = getSharedPreferences.getInt("idLogin", -1);
        recebeNotify = getSharedPreferences.getBoolean("recebeNotify",true);
        //qtNotificacao = getSharedPreferences.getInt("qtNotificacao", 0);
        //mostraNotificacao();
        if(recebeNotify)
       getNotificacoes(idLogin);
    }




    public void getNotificacoes(int idLogin)
    {
        IApi iApi = IApi.retrofit.create(IApi.class);
        final Call<List<Notificacoes>> callBuscaNotificacao = iApi.BuscaNotificacao(idLogin);
        callBuscaNotificacao.enqueue(new Callback<List<Notificacoes>>() {
            @Override
            public void onResponse(Call<List<Notificacoes>> call, Response<List<Notificacoes>> response) {

                switch (response.code())
                {
                    case 200:
                        List<Notificacoes> ListaNotificacao = response.body();
                        int qt = 0;
                        for(int x=0;x<ListaNotificacao.size();x++){
                            if(ListaNotificacao.get(x).getVisualizado() == 0){
                                qt++;
                            }
                        }

                        if(qt > 0)
                        {
                            mostraNotificacao();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Notificacoes>> call, Throwable t) {
            }
        });

    }

    public void mostraNotificacao(){
        String Titulo="Olá.";
        String Corpo = "Você possui novas notificações !!";
        Intent intent = new Intent(this, notificacao.class);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Titulo)
                .setContentText(Corpo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
