package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoCalle;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoCalle;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerTipoCalle extends AsyncTask<Void, Void, List<ETipoCalle>> {

    private final WeakReference<Context> context;

    public TObtenerTipoCalle(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(List<ETipoCalle> data) {
        super.onPostExecute( data );

        Log.i( "BD:ObtenerTipoCalle", String.valueOf( data.size() ) );
    }

    @Override
    protected  List<ETipoCalle> doInBackground(Void... voids) {

        DTipoCalle dao = database.getDatabase(context.get()).dTipoCalle();

        return dao.getAll();

    }
}
