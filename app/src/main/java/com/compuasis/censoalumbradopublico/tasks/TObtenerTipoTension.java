package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoTension;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoTension;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerTipoTension extends AsyncTask<Void, Void, List<ETipoTension>> {

    private final WeakReference<Context> context;
    public TObtenerTipoTension(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(List<ETipoTension> data) {
        super.onPostExecute( data );

        Log.i( "BD:ObtenerTipoTension", String.valueOf( data.size() ) );

    }

    @Override
    protected  List<ETipoTension> doInBackground(Void... voids) {

        DTipoTension dao = database.getDatabase(context.get()).dTipoTension();

        return dao.getAll();

    }
}
