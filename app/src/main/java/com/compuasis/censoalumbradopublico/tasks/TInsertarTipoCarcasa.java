package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoCarcasa;
import com.compuasis.censoalumbradopublico.data.DTipoPoste;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarTipoCarcasa extends AsyncTask<List<ETipoCarcasa>, Void, Void> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;

    public TInsertarTipoCarcasa(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new TObtenerTipoCarcasa( this.context.get(), this.fragment ).execute(  );
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<ETipoCarcasa>... data) {

        List<ETipoCarcasa> lista = data[0];

        DTipoCarcasa dao = database.getDatabase(this.context.get()).dTipoCarcasa();

        dao.deleteAll();
        for (ETipoCarcasa elemento : lista) {
            dao.insert( elemento );
        }
        Log.d( "InsertarTipoCarcasa", String.valueOf( lista.size() ) );

        return null;
    }
}
