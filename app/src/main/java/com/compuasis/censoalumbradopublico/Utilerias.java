package com.compuasis.censoalumbradopublico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public  class Utilerias {

    public static Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

    }

}
