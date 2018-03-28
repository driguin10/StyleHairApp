package com.stylehair.nerdsolutions.stylehair.Notification;



/**
 * Created by Rodrigo on 20/02/2018.
 */

public class Sender {
    public Notification notification;
    public String to;

    public Sender(Notification notification, String to) {
        this.notification = notification;
        this.to = to;
    }

    public Sender(Notification notification) {
        this.notification = notification;
        this.to = "/topics/AllNotifications";
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
}
