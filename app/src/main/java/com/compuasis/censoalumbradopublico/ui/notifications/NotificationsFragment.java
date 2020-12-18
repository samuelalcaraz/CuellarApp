package com.compuasis.censoalumbradopublico.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.services.TipoCarcasa;
import com.compuasis.censoalumbradopublico.services.TipoLampara;
import com.compuasis.censoalumbradopublico.services.TipoPoste;
import com.compuasis.censoalumbradopublico.tasks.TObtenerCensosCombo;
import com.compuasis.censoalumbradopublico.tasks.TObtenerTipoCarcasa;
import com.compuasis.censoalumbradopublico.tasks.TObtenerTipoLampara;
import com.compuasis.censoalumbradopublico.tasks.TObtenerTipoPoste;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    Spinner spCensos, spTipoPoste, spTipoCarcasa, spTipoLampara1, spTipoLampara2;

    NotificationsFragment fragment;

    SweetAlertDialog pDialog = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.fragment = this;
        notificationsViewModel =
                ViewModelProviders.of( this ).get( NotificationsViewModel.class );
        View root = inflater.inflate( R.layout.fragment_notifications, container, false );

        spCensos = root.findViewById( R.id.spCensos );
        spTipoPoste = root.findViewById( R.id.spTipoPoste );
        spTipoCarcasa = root.findViewById( R.id.spTipoCarcasa );
        spTipoLampara1 = root.findViewById( R.id.spTipoLampra1 );
        spTipoLampara2 = root.findViewById( R.id.spTipoLampra2 );

        new TObtenerCensosCombo( this.getContext(), fragment ).execute(  );
        new TObtenerTipoPoste( this.getContext(), this.fragment ).execute(  );
        new TObtenerTipoCarcasa( this.getContext(), this.fragment ).execute(  );
        new TObtenerTipoLampara( this.getContext(), this.fragment ).execute(  );

        return root;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate( savedInstanceState );
    }

    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.poste_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Actualizar":
                pDialog = new SweetAlertDialog(fragment.getContext(), SweetAlertDialog.PROGRESS_TYPE);

                pDialog.setTitleText("Actualizando catalogos");
                pDialog.setCancelable(false);
                pDialog.show();

                new TipoPoste(getContext(), fragment).execute();
                new TipoCarcasa( getContext(), fragment).execute();
                new TipoLampara( getContext(), fragment).execute();
                break;

            case "Nuevo":
              /*  txtDivision.setText( "" );
                txtZona.setText( "" );
                txtAgencia.setText( "" );
                txtCalle.setText( "" );
                rgCalle.setSelected( false );
                txtCalleMargen.setText( "" );
                chkCalleMargenIzquierda.setChecked( false );
                chkCalleMargenDerecha.setChecked( false );
                chkCalleMargenCentro.setChecked( false );
                txtManzana.setText( "" );
                rgTension.setSelected( false );
                txtEntreCalle1.setText( "" );
                txtEntreCalle2.setText( "" );
                txtPoblacionColonia.setText( "" );
                txtLocalidad.setText( "" );*/
                break;

            case "Guardar":
              /*  if(spEstados.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Debe seleccionar un estado").show();
                    break;
                }
                if(spMunicipios.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Debe seleccionar un municipio").show();
                    break;
                }
                if(rgCalle.getCheckedRadioButtonId() == -1) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Debe seleccionar un tipo de calle").show();
                    break;
                }
                if(rgTension.getCheckedRadioButtonId() == -1) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Debe seleccionar una tensión").show();
                    break;
                }


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

                if(!actualizar) {
                    new TInsertarCenso( fragment.getContext() ).execute( censo );
                    actualizar = true;
                } else {
                    new TActualizarCenso( fragment.getContext() ).execute( censo );
                }
*/
        }



        return super.onOptionsItemSelected(item);
    }

    public void fillTipoPoste(List<ETipoPoste> list) {

        ArrayAdapter<ETipoPoste> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        spTipoPoste.setAdapter( adapter );

    }

    public void fillTipoCarcasa(List<ETipoCarcasa> list){

        ArrayAdapter<ETipoCarcasa> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spTipoCarcasa.setAdapter( adapter );

    }

    public void fillTipoLampara(List<ETipoLampara> list){

        ArrayAdapter<ETipoLampara> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spTipoLampara1.setAdapter( adapter );

        ArrayAdapter<ETipoLampara> adapter2 = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spTipoLampara2.setAdapter( adapter2 );

        if(pDialog != null) {
            pDialog.cancel();
        }

    }

    public void fillCensos(List<ECenso> list){

        ArrayAdapter<ECenso> adapter = new ArrayAdapter<>( getContext(),
                android.R.layout.simple_spinner_item, list );

        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spCensos.setAdapter( adapter );

    }
}