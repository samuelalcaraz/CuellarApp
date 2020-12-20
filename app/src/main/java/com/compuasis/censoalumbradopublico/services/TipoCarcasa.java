package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.tasks.TInsertarTipoCarcasa;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipoCarcasa extends AsyncTask<Void, Void, List<ETipoCarcasa>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;

    private final String url = Services.Service_Base + "tiposcarcasas";

    NotificationsFragment fragment;

    public TipoCarcasa(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoCarcasa> s) {

        super.onPostExecute( s );

        new TInsertarTipoCarcasa( this.context.get(), this.fragment).execute( s );
    }

    @Override
    protected List<ETipoCarcasa> doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url( this.url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerTipoCacasa", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<ETipoCarcasa>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}