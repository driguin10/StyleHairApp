package com.stylehair.nerdsolutions.stylehair.Notification.backNotification;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Rodrigo on 19/02/2018.
 */

public class CDCInstanceIDService extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {

        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Common.currenToken = refreshedToken;

    }


}