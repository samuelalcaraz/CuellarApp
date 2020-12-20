package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.tasks.TInsertarTipoLampara;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TipoLampara extends AsyncTask<Void, Void, List<ETipoLampara>> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;

    private final String url = Services.Service_Base + "tiposlamparas";

    NotificationsFragment fragment;

    public TipoLampara(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoLampara> s) {

        super.onPostExecute( s );

        new TInsertarTipoLampara( this.context.get(), this.fragment ).execute( s );
    }

    @Override
    protected List<ETipoLampara> doInBackground(Void... voids) {

        Request request = new Request.Builder()
                .url( this.url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerTipoCacasa", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<ETipoLampara>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}