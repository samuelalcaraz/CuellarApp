package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.services.Cities;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerEstado extends AsyncTask<Void, Void, List<EEstado>> {

    private final WeakReference<Context> context;
    DashboardFragment fragment;
    public TObtenerEstado(Context context, DashboardFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<EEstado> eEstados) {
        super.onPostExecute( eEstados );

        Log.i( "BD:ObtenerEstados", String.valueOf( eEstados.size() ) );
        this.fragment.fillStates( eEstados );

    }

    @Override
    protected  List<EEstado> doInBackground(Void... voids) {

        DEstado dEstado = database.getDatabase(context.get()).dEstado();

        return dEstado.getAll();

    }
}
