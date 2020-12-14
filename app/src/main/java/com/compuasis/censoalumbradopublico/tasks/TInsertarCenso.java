package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DCenso;
import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarCenso extends AsyncTask<ECenso, Void, Void> {

private final WeakReference<Context> context;

    public TInsertarCenso(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(ECenso... censos) {

        ECenso censo = censos[0];

        DCenso dCenso = database.getDatabase(this.context.get()).dCenso();

        dCenso.insert( censo );


        return null;
    }
}
