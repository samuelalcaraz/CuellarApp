package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DPoste;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.ui.home.HomeFragment;
import com.compuasis.censoalumbradopublico.ui.postes.PostesFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerPostes extends AsyncTask<Integer, Void, List<EPoste>> {

    private final WeakReference<Context> context;
    PostesFragment fragment;

    public TObtenerPostes(Context context, PostesFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;

    }

    @Override
    protected void onPostExecute(List<EPoste> data) {
        super.onPostExecute( data );

        this.fragment.fillPostes ( data );
    }

    @Override
    protected  List<EPoste> doInBackground(Integer... data) {

        DPoste dao = database.getDatabase(context.get()).dPoste();

        return  dao.getByCenso(data[0]);

    }
}
