package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoCalle;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoCalle;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarTipoCalle extends AsyncTask<List<ETipoCalle>, Void, Void> {

    private final WeakReference<Context> context;

    public TInsertarTipoCalle(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new TObtenerTipoCalle( this.context.get() ).execute(  );
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<ETipoCalle>... data) {

        List<ETipoCalle> lista = data[0];

        DTipoCalle dao = database.getDatabase(this.context.get()).dTipoCalle();

        dao.deleteAll();
        for (ETipoCalle elemento : lista) {
            dao.insert( elemento );
        }
        Log.d( "InsertarTipoCalle", String.valueOf( lista.size() ) );

        return null;
    }
}
