package com.stylehair.nerdsolutions.stylehair.api;


import com.stylehair.nerdsolutions.stylehair.Notification.ReturnMessage;
import com.stylehair.nerdsolutions.stylehair.Notification.Sender;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by Rodrigo on 20/02/2018.
 */

public interface INotification {


    String WebService = "https://fcm.googleapis.com/";





    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAnzTOKs8:APA91bFZ1fSBTifV40epf-sRpLrH8woY-ZgVWCHFcvHnokfbP3HSt8lUj2AXc0KkHEn3jfV1_SlhCzjTurAp6wbsDJhOjnsMNAFg5UrRuel0nJuJEPdgcaxgo20OPxMdWgKqcq3O0s5t"
    })
    @POST("fcm/send")
    Call<ReturnMessage> enviarNotificacao(@Body Sender body);


    //******************* SERVICE RETROFIT ******************************
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    public static final Retrofit retrofit = new Retrofit.Builder()

            .baseUrl(WebService)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //*******************************************************************


}
