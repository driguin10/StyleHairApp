package com.stylehair.nerdsolutions.stylehair.Notification;



/**
 * Created by Rodrigo on 20/02/2018.
 */

public class Sender {
    public Notification notification;
    public String to;

    public Dataa data;

    public Sender(Notification notification, String to, Dataa data) {
        this.notification = notification;
        this.to = to;
        this.data = data;
    }

    public Sender(Notification notification,Dataa data) {
        this.notification = notification;
        this.to = "/topics/AllNotifications";
        this.data = data;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Dataa getDataa() {
        return data;
    }

    public void setDataa(Dataa data) {
        this.data = data;
    }
}
