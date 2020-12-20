package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ETipoCalle;
import com.compuasis.censoalumbradopublico.tasks.TInsertarTipoCalle;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipoCalle extends AsyncTask<Void, Void, List<ETipoCalle>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;

    private final String url = Services.Service_Base + "tiposcalles";

    public TipoCalle(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(List<ETipoCalle> s) {

        super.onPostExecute( s );

        new TInsertarTipoCalle( this.context.get()).execute( s );
    }

    @Override
    protected List<ETipoCalle> doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url( this.url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerTipoCacasa", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<ETipoCalle>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}