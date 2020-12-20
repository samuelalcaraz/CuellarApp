package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.tasks.TInsertarEstado;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

 public class States extends AsyncTask<Void, Void, List<EEstado>> {
    OkHttpClient client = new OkHttpClient();
     private final WeakReference<Context> context;
     DashboardFragment fragment;

     private final String url = Services.Service_Base + "estados";

     public States(Context context, DashboardFragment fragment)
     {
         this.context = new WeakReference<>(context);
         this.fragment = fragment;
     }


     @Override
     protected void onPostExecute(List<EEstado> s) {

         super.onPostExecute( s );

         new TInsertarEstado( this.context.get(), this.fragment ).execute( s );
     }

     @Override
     protected List<EEstado> doInBackground(Void... voids) {

         Request request = new Request.Builder()
                 .url( url )
                 .build();

         String res;
         try (Response response = client.newCall( request ).execute()) {
             res =  Objects.requireNonNull( response.body() ).string();

             Log.i("ObtenerEstados", res);
             return Utilerias.getGson().fromJson(res, new TypeToken<List<EEstado>>(){}.getType());


         } catch (IOException e) {
             Log.e("ObtenerEstados", e.getMessage());

             return  null;
         }

     }
 }