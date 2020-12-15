package com.compuasis.censoalumbradopublico.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.services.Cities;
import com.compuasis.censoalumbradopublico.services.States;
import com.compuasis.censoalumbradopublico.tasks.TInsertarCenso;
import com.compuasis.censoalumbradopublico.tasks.TInsertarEstado;
import com.compuasis.censoalumbradopublico.tasks.TObtenerEstado;
import com.compuasis.censoalumbradopublico.tasks.TObtenerMunicipio;
import com.compuasis.censoalumbradopublico.ui.MultipleChioceDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

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

    public DashboardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate( savedInstanceState );
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.fragment = this;
        dashboardViewModel =
                ViewModelProviders.of( this ).get( DashboardViewModel.class );

        View root = inflater.inflate( R.layout.fragment_dashboard, container, false );


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
                censo.IdMunicipio = municipio.IdMunicipio;
                censo.Division = txtDivision.getText().toString();
                censo.Zona = txtZona.getText().toString();
                censo.Agencia = txtAgencia.getText().toString();
                censo.Calle = txtCalle.getText().toString();
                censo.IdCalleTipo =Integer.parseInt( getActivity().findViewById( rgCalle.getCheckedRadioButtonId()).getTag().toString());
                censo.CalleMargen = txtCalleMargen.getText().toString();
                censo.CalleMargenIzquierda = chkCalleMargenIzquierda.isChecked();
                censo.CalleMargenDerecha = chkCalleMargenDerecha.isChecked();
                censo.CalleMargenCentro = chkCalleMargenCentro.isChecked();
                censo.Manzana = txtManzana.getText().toString();
                censo.IdTension = Integer.parseInt(getActivity().findViewById( rgTension.getCheckedRadioButtonId()).getTag().toString());
                censo.EntreCalle1 = txtEntreCalle1.getText().toString();
                censo.EntreCalle2 = txtEntreCalle2.getText().toString();
                censo.PoblacionColonia = txtPoblacionColonia.getText().toString();
                censo.Localidad = txtLocalidad.getText().toString();

                new TInsertarCenso( fragment.getContext() ).execute( censo );

            }
        } );










        new TObtenerEstado( this.getContext(), this.fragment ).execute(  );





        return  root;
    }



    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        //inflate menu
        inflater.inflate(R.menu.censo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.action_actualizar) {

             new States(getContext(), fragment).execute( "https://alcaraz.mx/hosting/censoap/services/states.php" );

            Toast.makeText(getActivity(), "Actualizando Estados y municipios", Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
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

