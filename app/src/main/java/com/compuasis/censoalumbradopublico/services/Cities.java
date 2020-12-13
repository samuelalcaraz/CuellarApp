package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.tasks.TInsertarEstado;
import com.compuasis.censoalumbradopublico.tasks.TInsertarMunicipio;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Cities extends AsyncTask<String, Void, List<EMunicipio>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;

    public Cities(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(List<EMunicipio> s) {

        super.onPostExecute( s );

        new TInsertarMunicipio( this.context.get() ).execute( s );
    }

    @Override
    protected List<EMunicipio> doInBackground(String... strings) {

        Request request = new Request.Builder()
                .url( strings[0] )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            return Utilerias.getGson().fromJson(res, new TypeToken<List<EMunicipio>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}