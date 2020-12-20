package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.tasks.TInsertarTipoPoste;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipoPoste extends AsyncTask<Void, Void, List<ETipoPoste>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;
    private final String url = Services.Service_Base + "tipospostes";

    NotificationsFragment frament;
    public TipoPoste(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.frament = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoPoste> s) {

        super.onPostExecute( s );

        new TInsertarTipoPoste( this.context.get(), this.frament ).execute( s );
    }

    @Override
    protected List<ETipoPoste> doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url( url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerTipoPoste", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<ETipoPoste>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}