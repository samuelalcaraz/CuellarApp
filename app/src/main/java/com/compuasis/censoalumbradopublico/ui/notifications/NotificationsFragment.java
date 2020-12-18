package com.compuasis.censoalumbradopublico.ui.notifications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static java.lang.Math.round;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    Spinner spCensos, spTipoPoste, spTipoCarcasa, spTipoLampara1, spTipoLampara2;

    TextInputEditText txtGeoX, txtGeoY;

    ImageView ivFoto;

    NotificationsFragment fragment;

    SweetAlertDialog pDialog = null;

    private FusedLocationProviderClient fusedLocationClient;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivFoto.setImageBitmap(imageBitmap);
        }
    }

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

        txtGeoX = root.findViewById( R.id.txtGeoX );
        txtGeoY = root.findViewById( R.id.txtGeoY );

        ivFoto = root.findViewById( R.id.ivFoto );

        ivFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        } );

        new TObtenerCensosCombo( this.getContext(), fragment ).execute();
        new TObtenerTipoPoste( this.getContext(), this.fragment ).execute();
        new TObtenerTipoCarcasa( this.getContext(), this.fragment ).execute();
        new TObtenerTipoLampara( this.getContext(), this.fragment ).execute();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient( this.getContext() );

        if (ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }



        fusedLocationClient.getLastLocation()
                .addOnSuccessListener( this.getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double x = location.getLatitude();
                            double y = location.getLongitude();
                            txtGeoX.setText( String.format(Locale.US,"%.6f", x) );
                            txtGeoY.setText( String.format(Locale.US,"%.6f", y) );
                        } else {
                            txtGeoX.setText( "" );
                            txtGeoY.setText( "" );
                        }
                    }
                } );

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(250); // 1 segundo


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText( fragment.getContext(), "Si ubicación", Toast.LENGTH_SHORT ).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        double x = location.getLatitude();
                        double y = location.getLongitude();
                        double a = location.getAccuracy();
                        txtGeoX.setText( String.format(Locale.US, "%.6f", x ) );
                        txtGeoY.setText( String.format(Locale.US, "%.6f", y ) );

                        Toast.makeText( fragment.getContext(), "Precisión: " + String.format(Locale.US, "%.2f", a ) + " metros", Toast.LENGTH_SHORT ).show();
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    } else {
                        Toast.makeText( fragment.getContext(), "Si ubicación", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        };


        return root;


    }

    public void ObtenerUltimaLocalizacion() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
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

    @SuppressLint("MissingPermission")
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

            case "GPS":
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

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