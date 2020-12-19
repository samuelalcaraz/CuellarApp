package com.compuasis.censoalumbradopublico.ui.postes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.adapters.CensosAdapter;
import com.compuasis.censoalumbradopublico.adapters.PostesAdapter;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.tasks.TObtenerPostes;
import com.compuasis.censoalumbradopublico.ui.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PostesFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView rvCensos;

    PostesFragment fragment;

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
        homeViewModel =
                ViewModelProviders.of( this ).get( HomeViewModel.class );
        View root = inflater.inflate( R.layout.fragment_home, container, false );
      /*  final TextView textView = root.findViewById( R.id.text_home );
        homeViewModel.getText().observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText( s );
            }
        } );*/


        rvCensos = (RecyclerView) root.findViewById(R.id.rvCensos);

        this.fragment = this;

        new TObtenerPostes( fragment.getContext(), fragment ).execute( 1 );


        return root;
    }

    public void fillPostes(List<EPoste> list) {

        PostesAdapter adapter = new PostesAdapter(list);
        rvCensos.setAdapter(adapter);
        rvCensos.setLayoutManager(new LinearLayoutManager(fragment.getContext()));

    }
}