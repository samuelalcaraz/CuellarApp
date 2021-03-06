package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.DTipoPoste;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.services.Cities;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarTipoPoste extends AsyncTask<List<ETipoPoste>, Void, Void> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;

    public TInsertarTipoPoste(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new TObtenerTipoPoste( this.context.get(), this.fragment ).execute(  );
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<ETipoPoste>... data) {

        List<ETipoPoste> lista = data[0];

        DTipoPoste dao = database.getDatabase(this.context.get()).dTipoPoste();

        dao.deleteAll();
        for (ETipoPoste elemento : lista) {
            dao.insert( elemento );
        }
        Log.d( "InsertarTipoPoste", String.valueOf( lista.size() ) );

        return null;
    }
}
