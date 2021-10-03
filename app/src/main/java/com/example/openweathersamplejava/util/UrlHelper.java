package com.example.openweathersamplejava.util;

import com.example.openweathersamplejava.constants.Url;

public class UrlHelper {
    public static String getFullImgUrl(String path) {
        return Url.OPEN_WEATHER_IMG_URL + path + "@2x.png";
    }
}
