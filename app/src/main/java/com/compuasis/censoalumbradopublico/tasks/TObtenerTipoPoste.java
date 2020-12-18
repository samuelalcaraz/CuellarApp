package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.DTipoPoste;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerTipoPoste extends AsyncTask<Void, Void, List<ETipoPoste>> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;
    public TObtenerTipoPoste(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoPoste> data) {
        super.onPostExecute( data );

        Log.i( "BD:ObtenerTipoPoste", String.valueOf( data.size() ) );
        this.fragment.fillTipoPoste( data );

    }

    @Override
    protected  List<ETipoPoste> doInBackground(Void... voids) {

        DTipoPoste dao = database.getDatabase(context.get()).dTipoPoste();

        return dao.getAll();

    }
}
