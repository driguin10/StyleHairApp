package com.stylehair.nerdsolutions.stylehair.Notification;



public class Notification {
    public String body;
    public String title;

    public Notification(String body, String titulo) {
        this.body = body;
        this.title = titulo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitulo() {
        return title;
    }

    public void setTitulo(String titulo) {
        this.title = titulo;
    }
}
