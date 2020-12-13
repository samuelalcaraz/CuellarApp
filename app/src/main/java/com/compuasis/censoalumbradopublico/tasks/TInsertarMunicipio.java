package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.DimenRes;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.DMunicipio;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarMunicipio extends AsyncTask<List<EMunicipio>, Void, Void> {

private final WeakReference<Context> context;

    public TInsertarMunicipio(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<EMunicipio>... data) {

        List<EMunicipio> lista = data[0];

        DMunicipio dao = database.getDatabase(this.context.get()).dMunicipio();

        dao.deleteAll();
        for (EMunicipio item : lista) {
            dao.insert( item );
        }

        return null;
    }
}
