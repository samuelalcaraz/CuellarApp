package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ETipoTension;
import com.compuasis.censoalumbradopublico.tasks.TInsertarTipoTension;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipoTension extends AsyncTask<Void, Void, List<ETipoTension>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;
    private final String url = Services.Service_Base + "tipostensiones";

    public TipoTension(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(List<ETipoTension> s) {

        super.onPostExecute( s );

        new TInsertarTipoTension( this.context.get() ).execute( s );
    }

    @Override
    protected List<ETipoTension> doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url( url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerTipoTension", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<ETipoTension>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}