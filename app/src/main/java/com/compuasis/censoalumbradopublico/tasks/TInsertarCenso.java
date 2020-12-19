package com.compuasis.censoalumbradopublico.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.compuasis.censoalumbradopublico.data.DCenso;
import com.compuasis.censoalumbradopublico.data.DEstado;
import com.compuasis.censoalumbradopublico.data.database;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TInsertarCenso extends AsyncTask<ECenso, Void, Void> {

private final WeakReference<Context> context;

    public TInsertarCenso(@Nullable Context context)
    {
        this.context = new WeakReference<>(context);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute( aVoid );

        new SweetAlertDialog(this.context.get(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("El Censo seguardó correctamente")
                .show();
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(ECenso... censos) {

        ECenso censo = censos[0];

        DCenso dCenso = database.getDatabase(this.context.get()).dCenso();

        dCenso.insert( censo );


        return null;
    }
}
