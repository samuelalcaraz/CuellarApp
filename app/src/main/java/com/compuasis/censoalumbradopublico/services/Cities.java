package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.tasks.TInsertarMunicipio;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
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
    
    private final String url = Services.Service_Base + "municipios";

    DashboardFragment fragment;
    public Cities(Context context, DashboardFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<EMunicipio> s) {

        super.onPostExecute( s );

        new TInsertarMunicipio( this.context.get(), this.fragment ).execute( s );
    }

    @Override
    protected List<EMunicipio> doInBackground(String... strings) {

        Request request = new Request.Builder()
                .url( url )
                .build();

        String res;
        try (Response response = client.newCall( request ).execute()) {
            res =  Objects.requireNonNull( response.body() ).string();

            Log.i( "ObtenerMunicipio", res );
            return Utilerias.getGson().fromJson(res, new TypeToken<List<EMunicipio>>(){}.getType());

        } catch (IOException e) {
            return  null;
        }

    }
}