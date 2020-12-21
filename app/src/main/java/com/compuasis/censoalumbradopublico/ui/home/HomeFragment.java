package com.compuasis.censoalumbradopublico.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.adapters.CensosAdapter;
import com.compuasis.censoalumbradopublico.entities.ECensoPoste;
import com.compuasis.censoalumbradopublico.services.CensoUpload;
import com.compuasis.censoalumbradopublico.services.EResult;
import com.compuasis.censoalumbradopublico.tasks.TObtenerCensos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    RecyclerView rvCensos;

    HomeFragment fragment;

    List<ECensoPoste> censos;

    SweetAlertDialog pDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.lista_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate( R.layout.fragment_home, container, false );

        this.fragment = this;

        rvCensos = root.findViewById(R.id.rvCensos);

        new TObtenerCensos( fragment.getContext(), fragment ).execute(  );

        return root;
    }

    public void fillCensos(List<ECensoPoste> censos) {

        this.censos = censos;
        CensosAdapter adapter = new CensosAdapter(censos);
        rvCensos.setAdapter(adapter);
        rvCensos.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

    }

    public void respuestaUpload(EResult result) {

        this.pDialog.cancel();

        if(result.Exito) {
            new SweetAlertDialog(this.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("La información se subió correctamente.")
                    .show();
        } else {
            new SweetAlertDialog(this.getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(result.Mensaje)
                    .show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if ("Upload".equals( item.getTitle().toString() )) {

            if(this.censos != null && this.censos.size() >= 1) {

                pDialog = new SweetAlertDialog(fragment.getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("Subiendo información a la nube");
                pDialog.setCancelable(false);
                pDialog.show();

                new CensoUpload( fragment.getContext(), fragment ).execute( this.censos );

            }

        }

        return super.onOptionsItemSelected(item);
    }
}