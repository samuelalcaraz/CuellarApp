package com.compuasis.censoalumbradopublico.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.tasks.TObtenerEstado;
import com.compuasis.censoalumbradopublico.tasks.TObtenerMunicipio;
import com.compuasis.censoalumbradopublico.ui.MultipleChioceDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment implements MultipleChioceDialogFragment.onMultipleChioceListener {

    private DashboardViewModel dashboardViewModel;

    DashboardFragment fragment;

    Spinner spEstados, spMunicipios;
    RadioGroup rgCalle, rgTension;
    Button btnGuardar;

    TextInputEditText txtDivision, txtZona, txtAgencia, txtCalle, txtCalleMargen, txtManzana,
        txtEntreCalle1,  txtEntreCalle2, txtPoblacionColonia, txtLocalidad;
    CheckBox chkCalleMargenDerecha, chkCalleMargenIzquierda, chkCalleMargenCentro;

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

        rgCalle = root.findViewById( R.id.rgCalle );
        rgTension = root.findViewById( R.id.rgTension );

        txtDivision = root.findViewById(R.id.txtDivision );
        txtZona = root.findViewById( R.id.txtZona );
        txtAgencia = root.findViewById( R.id.txtAgencia );
        txtCalle = root.findViewById( R.id.txtCalle );
        txtCalleMargen = root.findViewById( R.id.txtCalleMargen );
        txtManzana = root.findViewById(R.id.txtManzana );
        txtEntreCalle1 = root.findViewById( R.id.txtEntreCalle1 );
        txtEntreCalle2 = root.findViewById( R.id.txtEntreCalle2 );
        txtPoblacionColonia = root.findViewById( R.id.txtPoblacionColonia );
        txtLocalidad = root.findViewById( R.id.txtLocalidad );

        chkCalleMargenDerecha = root.findViewById( R.id.chkCalleMargenDerecha );
        chkCalleMargenIzquierda = root.findViewById( R.id.chkCalleMargenIzquierda );
        chkCalleMargenCentro = root.findViewById( R.id.chkCalleMargenCentro );

        btnGuardar = root.findViewById( R.id.btnGuardar );

        btnGuardar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ECenso censo = new ECenso();
                EMunicipio municipio = (EMunicipio) spMunicipios.getSelectedItem();





            }
        } );







        //new Cities(getContext()).execute( "https://alcaraz.mx/hosting/censoap/services/cities.php" );
        //new States(getContext()).execute( "https://alcaraz.mx/hosting/censoap/services/states.php" );



        new TObtenerEstado( getContext(), this ).execute(  );



        return  root;
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

