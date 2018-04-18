package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stylehair.nerdsolutions.stylehair.R;
import com.stylehair.nerdsolutions.stylehair.Notification.bancoNotificacoes.BancoNotifyController;
import com.stylehair.nerdsolutions.stylehair.Notification.notificacao;


/**
 * Created by Rodrigo on 19/02/2018.
 */

public class CDCMessasingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification());
    }

    private void showNotification(RemoteMessage.Notification notification) {

        SharedPreferences getSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

       int idLogin = getSharedPreferences.getInt("idLogin", -1);

        String Titulo = notification.getTitle();
        String Corpo = notification.getBody();
        String[] info = Corpo.split("§");

        Intent intent = new Intent(this, notificacao.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Titulo)
                .setContentText(info[0])
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

        BancoNotifyController crud = new BancoNotifyController(getBaseContext());
        String nomeSalao = info[0];
        String menssagem = info[1];
        String hora = info[2];
        String visualizado = "0";

        String retorno = crud.insereDado(Titulo,menssagem,hora,visualizado,nomeSalao,String.valueOf(idLogin));


       /* Intent i = new Intent(this, notificacao.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);*/

    }
}
