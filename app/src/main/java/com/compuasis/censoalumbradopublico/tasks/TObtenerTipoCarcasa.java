package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.compuasis.censoalumbradopublico.data.DTipoCarcasa;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.ui.notifications.NotificationsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerTipoCarcasa extends AsyncTask<Void, Void, List<ETipoCarcasa>> {

    private final WeakReference<Context> context;
    NotificationsFragment fragment;
    public TObtenerTipoCarcasa(Context context, NotificationsFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;
    }

    @Override
    protected void onPostExecute(List<ETipoCarcasa> data) {
        super.onPostExecute( data );

        Log.i( "BD:ObtenerTipoCarcasa", String.valueOf( data.size() ) );
        this.fragment.fillTipoCarcasa( data );

    }

    @Override
    protected  List<ETipoCarcasa> doInBackground(Void... voids) {

        DTipoCarcasa dao = database.getDatabase(context.get()).dTipoCarcasa();

        return dao.getAll();

    }
}
