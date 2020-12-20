package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.ECensoPoste;
import com.compuasis.censoalumbradopublico.ui.home.HomeFragment;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CensoUpload extends AsyncTask<List<ECensoPoste>, Void, Void> {

    OkHttpClient client = new OkHttpClient();
    private final WeakReference<Context> context;
    private final String url = Services.Service_Base + "censos";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    HomeFragment frament;
    public CensoUpload(Context context, HomeFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.frament = fragment;
    }
    
    @Override
    protected Void doInBackground(List<ECensoPoste>... data) {

        // TODO Borrar la lista de censos y postes
        // TODO Actualizar RV

        String json = Utilerias.getGson().toJson( data[0] );
        String res = "";

        RequestBody body = RequestBody.create( json, JSON );
        Request request = new Request.Builder()
                .url( url )
                .post( body )
                .build();
        try (Response response = client.newCall( request ).execute()) {


            res = response.body().string();
            Log.i( "ObtenerTipoPoste", res );
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}