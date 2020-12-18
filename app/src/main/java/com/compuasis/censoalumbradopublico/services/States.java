package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.tasks.TInsertarEstado;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.Console;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

 public class States extends AsyncTask<String, Void, List<EEstado>> {
    OkHttpClient client = new OkHttpClient();
     private final WeakReference<Context> context;
     DashboardFragment fragment;

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
     protected List<EEstado> doInBackground(String... strings) {

         Request request = new Request.Builder()
                 .url( strings[0] )
                 .build();

         String res = null;
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