package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarEstado extends AsyncTask<List<EEstado>, Void, Void> {

private final WeakReference<Context> context;

    public TInsertarEstado(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<EEstado>... estados) {

        List<EEstado> listaEstados = estados[0];

        DEstado dEstado = database.getDatabase(this.context.get()).dEstado();

        dEstado.deleteAll();
        for (EEstado estado : listaEstados) {
            dEstado.insert( estado );
        }

        return null;
    }
}
