package com.compuasis.censoalumbradopublico.services;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.Utilerias;
import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.tasks.TInsertarEstado;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

 public class States extends AsyncTask<String, Void, List<EEstado>> {
    OkHttpClient client = new OkHttpClient();
     private final WeakReference<Context> context;

     public States(Context context)
     {
         this.context = new WeakReference<>(context);
     }

     @Override
     protected void onPostExecute(List<EEstado> s) {

         super.onPostExecute( s );

         new TInsertarEstado( this.context.get() ).execute( s );
     }

     @Override
     protected List<EEstado> doInBackground(String... strings) {

         Request request = new Request.Builder()
                 .url( strings[0] )
                 .build();

         String res = null;
         try (Response response = client.newCall( request ).execute()) {
             res =  Objects.requireNonNull( response.body() ).string();

             return Utilerias.getGson().fromJson(res, new TypeToken<List<EEstado>>(){}.getType());

         } catch (IOException e) {
             return  null;
         }

     }
 }