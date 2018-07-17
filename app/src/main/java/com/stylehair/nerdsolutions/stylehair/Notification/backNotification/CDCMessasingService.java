package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;
import com.stylehair.nerdsolutions.stylehair.telas.principal;


/**
 * Created by Rodrigo on 19/02/2018.
 */

public class CDCMessasingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification(),remoteMessage);
    }

    private void showNotification(RemoteMessage.Notification notification,RemoteMessage remoteMessage) {
        String NomeSalao = "";
        String DataNot = "";
        String Titulo = "...";
        String Corpo = "Houve um erro na notificação!!";
        if (remoteMessage.getData().size() > 0) {
             NomeSalao = remoteMessage.getData().get("salao");
            DataNot =  remoteMessage.getData().get("data");
        }
        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

       int idLogin = getSharedPreferences.getInt("idLogin", -1);

       if(!notification.getTitle().equals(""))
         Titulo = notification.getTitle();

        if(!notification.getBody().equals(""))
         Corpo = notification.getBody();


        Intent intent = new Intent(this, notificacao.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Titulo)
                .setContentText(NomeSalao)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

        BancoNotifyController crud = new BancoNotifyController(getBaseContext());

        String retorno = crud.insereDado(Titulo,Corpo,DataNot,"0",NomeSalao,String.valueOf(idLogin));
      //  Log.d("xex",retorno);

       /* Intent i = new Intent(this, notificacao.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);*/





    }
}
