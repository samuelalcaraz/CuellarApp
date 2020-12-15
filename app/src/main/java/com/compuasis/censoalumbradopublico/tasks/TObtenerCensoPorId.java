package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DCenso;
import com.compuasis.censoalumbradopublico.data.DMunicipio;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.home.HomeFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerCensoPorId extends AsyncTask<Void, Void, ECenso> {

    private final WeakReference<Context> context;
    HomeFragment fragment;
    int id;
    public TObtenerCensoPorId(Context context, HomeFragment fragment, int id)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
        this.id = id;
    }

    @Override
    protected void onPostExecute(ECenso data) {
        super.onPostExecute( data );

       // this.fragment.fillCensos ( data );
    }

    @Override
    protected  ECenso doInBackground(Void... voids) {

        DCenso dao = database.getDatabase(context.get()).dCenso();

        return  dao.getById(this.id);

    }
}
