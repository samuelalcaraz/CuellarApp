package com.compuasis.censoalumbradopublico.ui.postes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.adapters.PostesAdapter;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.tasks.TObtenerCensosComboRv;
import com.compuasis.censoalumbradopublico.tasks.TObtenerPostes;
import com.compuasis.censoalumbradopublico.ui.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostesFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView rvPostes;

    Spinner spCensos;
    PostesFragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
       // setHasOptionsMenu(true);
        super.onCreate( savedInstanceState );
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.lista_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View root = inflater.inflate( R.layout.fragment_postes, container, false );



        rvPostes =  root.findViewById(R.id.rvPostes);
        spCensos = root.findViewById( R.id.spCensos );

        this.fragment = this;

        spCensos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                ECenso data = (ECenso) parent.getSelectedItem();
                if(getContext() != null) {
                    new TObtenerPostes( fragment.getContext(), fragment ).execute( data.Uuid );
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(getContext() != null) {
            new TObtenerCensosComboRv( this.getContext(), fragment ).execute();
        }


        return root;
    }

    public void fillCensos(List<ECenso> list){

        ArrayAdapter<ECenso> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spCensos.setAdapter( adapter );

    }

    public void fillPostes(List<EPoste> list) {

        PostesAdapter adapter = new PostesAdapter(list);
        rvPostes.setAdapter(adapter);
        rvPostes.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

    }
}