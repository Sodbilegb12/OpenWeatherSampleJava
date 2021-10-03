package com.example.openweathersamplejava.model;

public class WeatherModel {
    private String icon;
    private String title;
    private String body;

    public WeatherModel(String icon, String title, String body) {
        this.icon = icon;
        this.title = title;
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
