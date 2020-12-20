package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoTension;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoTension;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarTipoTension extends AsyncTask<List<ETipoTension>, Void, Void> {

    private final WeakReference<Context> context;

    public TInsertarTipoTension(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new TObtenerTipoTension( this.context.get() ).execute(  );
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<ETipoTension>... data) {

        List<ETipoTension> lista = data[0];

        DTipoTension dao = database.getDatabase(this.context.get()).dTipoTension();

        dao.deleteAll();
        for (ETipoTension elemento : lista) {
            dao.insert( elemento );
        }
        Log.d( "InsertarTipoTension", String.valueOf( lista.size() ) );

        return null;
    }
}
