package com.compuasis.censoalumbradopublico.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.tasks.TObtenerEstado;
import com.compuasis.censoalumbradopublico.tasks.TObtenerMunicipio;
import com.compuasis.censoalumbradopublico.ui.MultipleChioceDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements MultipleChioceDialogFragment.onMultipleChioceListener {

    private DashboardViewModel dashboardViewModel;

    DashboardFragment fragment;

    Spinner spEstados, spMunicipios, spTension;
    TextView tvCalleMargen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.fragment = this;
        dashboardViewModel =
                ViewModelProviders.of( this ).get( DashboardViewModel.class );

        View root = inflater.inflate( R.layout.fragment_dashboard, container, false );

  /*      final TextView textView = root.findViewById( R.id.text_dashboard );

        dashboardViewModel.getText().observe( getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText( s );
            }
        } );
*/

        spEstados = root.findViewById( R.id.spEstados );
        spMunicipios = root.findViewById( R.id.spMunicpios );







        //new Cities(getContext()).execute( "https://alcaraz.mx/hosting/censoap/services/cities.php" );
        //new States(getContext()).execute( "https://alcaraz.mx/hosting/censoap/services/states.php" );



        new TObtenerEstado( getContext(), this ).execute(  );



        return  root;
    }



    private void fillTensions() {

        String[] items = { "Baja", "Media"};
        ArrayAdapter<String> adapter  = new ArrayAdapter<>( getContext(), android.R.layout.simple_spinner_dropdown_item,  items);
        spTension.setAdapter( adapter );

    }

    public void fillStates(List<EEstado> stateList){

        ArrayAdapter<EEstado> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, stateList );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spEstados.setAdapter( adapter );

        final DashboardFragment fragment = this;
        spEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EEstado state = (EEstado) parent.getSelectedItem();
                new TObtenerMunicipio( getContext(), (DashboardFragment) fragment, state.IdEstado ).execute(  );

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void fillCities(List<EMunicipio> list){

        ArrayAdapter<EMunicipio> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spMunicipios.setAdapter( adapter );


    }


    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPossitiveButtonClicked(String[] list, ArrayList<String> selectedItems) {

    }
}

