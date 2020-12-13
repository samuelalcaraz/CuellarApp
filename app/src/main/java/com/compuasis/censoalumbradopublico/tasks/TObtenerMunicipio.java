package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.DMunicipio;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerMunicipio extends AsyncTask<Void, Void, List<EMunicipio>> {

    private final WeakReference<Context> context;
    DashboardFragment fragment;
    int idEstado;
    public TObtenerMunicipio(Context context, DashboardFragment fragment, int idEstado)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
        this.idEstado = idEstado;
    }

    @Override
    protected void onPostExecute(List<EMunicipio> data) {
        super.onPostExecute( data );

        this.fragment.fillCities ( data );
    }

    @Override
    protected  List<EMunicipio> doInBackground(Void... voids) {

        DMunicipio dao = database.getDatabase(context.get()).dMunicipio();

        return  dao.getByState(this.idEstado);

    }
}
