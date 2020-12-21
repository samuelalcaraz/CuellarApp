package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DCenso;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.ui.postes.PostesFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class TObtenerCensosComboRv extends AsyncTask<Void, Void, List<ECenso>> {

    private final WeakReference<Context> context;
    PostesFragment fragment;

    public TObtenerCensosComboRv(Context context, PostesFragment fragment)
    {
        this.context = new WeakReference<>(context);
        this.fragment = fragment;

    }

    @Override
    protected void onPostExecute(List<ECenso> data) {
        super.onPostExecute( data );

        this.fragment.fillCensos ( data );
    }

    @Override
    protected  List<ECenso> doInBackground(Void... voids) {

        DCenso dao = database.getDatabase(context.get()).dCenso();

        return  dao.getAll();

    }
}
