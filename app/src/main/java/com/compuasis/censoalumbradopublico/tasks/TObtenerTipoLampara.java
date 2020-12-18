package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoLampara;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerTipoLampara extends AsyncTask<Void, Void, List<ETipoLampara>> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;
    public TObtenerTipoLampara(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoLampara> data) {
        super.onPostExecute( data );

        Log.i( "BD:ObtenerTipoLampara", String.valueOf( data.size() ) );
        this.fragment.fillTipoLampara( data );

    }

    @Override
    protected  List<ETipoLampara> doInBackground(Void... voids) {

        DTipoLampara dao = database.getDatabase(context.get()).dTipoLampara();

        return dao.getAll();

    }
}
