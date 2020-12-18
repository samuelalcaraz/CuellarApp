package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoLampara;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarTipoLampara extends AsyncTask<List<ETipoLampara>, Void, Void> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;

    public TInsertarTipoLampara(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new TObtenerTipoLampara( this.context.get(), this.fragment ).execute(  );
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(List<ETipoLampara>... data) {

        List<ETipoLampara> lista = data[0];

        DTipoLampara dao = database.getDatabase(this.context.get()).dTipoLampara();

        dao.deleteAll();
        for (ETipoLampara elemento : lista) {
            dao.insert( elemento );
        }
        Log.d( "InsertarTipoLampara", String.valueOf( lista.size() ) );

        return null;
    }
}
