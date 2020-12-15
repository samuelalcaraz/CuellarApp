package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.DimenRes;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.DMunicipio;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarMunicipio extends AsyncTask<List<EMunicipio>, Void, Void> {

    private final WeakReference<Context> context;
    DashboardFragment fragment;

    public TInsertarMunicipio(Context context, DashboardFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        new TObtenerEstado( this.context.get(), this.fragment ).execute(  );
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
        Log.d( "InsertarMunicipios", String.valueOf( lista.size() ) );

        return null;
    }


}
