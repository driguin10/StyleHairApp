package com.stylehair.nerdsolutions.stylehair.auxiliar;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class TopicoNotificacao {
    public void addTopico(String topico)
    {
        FirebaseMessaging.getInstance().subscribeToTopic(topico);
    }

    public void removeTopico(String topico)
    {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topico);
    }


}
