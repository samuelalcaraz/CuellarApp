package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.services.Cities;
import com.compuasis.censoalumbradopublico.services.TipoPoste;
import com.compuasis.censoalumbradopublico.ui.dashboard.DashboardFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TInsertarEstado extends AsyncTask<List<EEstado>, Void, Void> {

    private final WeakReference<Context> context;
    DashboardFragment fragment;

    public TInsertarEstado(Context context, DashboardFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        new TipoPoste( context.get() ).execute(  "https://alcaraz.mx/hosting/censoap/services/tipoposte.php");
        new Cities(context.get(), fragment).execute( "https://alcaraz.mx/hosting/censoap/services/cities.php" );

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
        Log.d( "InsertarEstados", String.valueOf( listaEstados.size() ) );

        return null;
    }
}
