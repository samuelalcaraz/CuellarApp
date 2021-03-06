package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DPoste;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.EPoste;

import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TInsertarPoste extends AsyncTask<EPoste, Void, Void> {

private final WeakReference<Context> context;

    public TInsertarPoste(Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new SweetAlertDialog(this.context.get(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("El Poste se guardó correctamente")
                .show();
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(EPoste... data) {

        EPoste item = data[0];

        DPoste dPoste = database.getDatabase(this.context.get()).dPoste();

        dPoste.insert( item );


        return null;
    }
}
