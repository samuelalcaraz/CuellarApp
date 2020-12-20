package com.compuasis.censoalumbradopublico.ui.notifications;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.services.TipoCarcasa;
import com.compuasis.censoalumbradopublico.services.TipoLampara;
import com.compuasis.censoalumbradopublico.services.TipoPoste;
import com.compuasis.censoalumbradopublico.tasks.TActualizarPoste;
import com.compuasis.censoalumbradopublico.tasks.TInsertarPoste;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment {

    Spinner spCensos, spTipoPoste, spTipoCarcasa, spTipoLampara1, spTipoLampara2;

    TextInputEditText txtIdPoste, txtCantidad1, txtWatts1, txtCarga1, txtCantidad2, txtWatts2, txtCarga2, txtEquipoAux, txtGeoX, txtGeoY;

    CheckBox chkCondicionPoste, chkCondicionLampara1, chkCondicionLampara2;

    ImageView ivFoto;

    NotificationsFragment fragment;

    SweetAlertDialog pDialog = null;


    private FusedLocationProviderClient fusedLocationClient;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    boolean actualizar = false;

    String currentPhotoPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {



            File f = new File( currentPhotoPath );
            //Bitmap bitmap = Imagen.decodeSampledBitmapFromFile(currentPhotoPath,1024,768);
            Bitmap bitmap = BitmapFactory.decodeFile( currentPhotoPath );

            ivFoto.setImageBitmap(bitmap);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.fragment = this;
        View root = inflater.inflate( R.layout.fragment_notifications, container, false );

        spCensos = root.findViewById( R.id.spCensos );
        spTipoPoste = root.findViewById( R.id.spTipoPoste );
        spTipoCarcasa = root.findViewById( R.id.spTipoCarcasa );
        spTipoLampara1 = root.findViewById( R.id.spTipoLampra1 );
        spTipoLampara2 = root.findViewById( R.id.spTipoLampra2 );

        txtIdPoste = root.findViewById( R.id.txtIdPoste );
        txtCantidad1 = root.findViewById( R.id.txtCantidad1 );
        txtWatts1 = root.findViewById( R.id.txtWatts1 );
        txtCarga1 = root.findViewById( R.id.txtCarga1 );
        txtCantidad2 = root.findViewById( R.id.txtCantidad2 );
        txtWatts2 = root.findViewById( R.id.txtWatts2 );
        txtCarga2 = root.findViewById( R.id.txtCarga2 );
        txtEquipoAux = root.findViewById( R.id.txtEquipoAux );
        txtGeoX = root.findViewById( R.id.txtGeoX );
        txtGeoY = root.findViewById( R.id.txtGeoY );

        chkCondicionPoste = root.findViewById( R.id.chkCondicionPoste );
        chkCondicionLampara1 = root.findViewById( R.id.chkCondicionLampara1 );
        chkCondicionLampara2 = root.findViewById( R.id.chkCondicionLampara2 );

        ivFoto = root.findViewById( R.id.ivFoto );

        ivFoto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        } );


        if(getContext() != null) {
            new TObtenerCensosCombo( this.getContext(), fragment ).execute();
            new TObtenerTipoPoste( this.getContext(), this.fragment ).execute();
            new TObtenerTipoCarcasa( this.getContext(), this.fragment ).execute();
            new TObtenerTipoLampara( this.getContext(), this.fragment ).execute();
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient( this.getContext() );

        if(getActivity() != null) {
            if (ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission( this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ){


                ActivityCompat.requestPermissions( getActivity(),
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_LOCATION );

            } else {
                ActivityCompat.requestPermissions( getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1 );
            }
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



    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions,
                                           @NotNull int[] grantResults){
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission( getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText( getContext(), "Permisos concedidos", Toast.LENGTH_SHORT ).show();
                }
            } else {
                Toast.makeText( getContext(), "Permisos denegados", Toast.LENGTH_SHORT ).show();
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = fragment.getActivity().getExternalFilesDir( Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(fragment.getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @SuppressLint({"MissingPermission", "WrongThread"})
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Actualizar":
                pDialog = new SweetAlertDialog(fragment.getContext(), SweetAlertDialog.PROGRESS_TYPE);

                pDialog.setTitleText("Actualizando catalogos");
                pDialog.setCancelable(false);
                pDialog.show();

                if(getContext() != null) {
                    new TipoPoste( getContext(), fragment ).execute();
                    new TipoCarcasa( getContext(), fragment ).execute();
                    new TipoLampara( getContext(), fragment ).execute();
                }
                break;

            case "GPS":
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                break;

            case "Nuevo":
                txtIdPoste.setText( "" );
                txtCantidad1.setText( "" );
                txtWatts1.setText( "" );
                txtCarga1.setText( "" );
                txtCantidad2.setText( "" );
                txtWatts2.setText( "" );
                txtCarga2.setText( "" );
                txtEquipoAux.setText( "" );
                txtGeoX.setText( "" );
                txtGeoY.setText( "" );

                chkCondicionPoste.setChecked( false );
                chkCondicionLampara1.setChecked( false );
                chkCondicionLampara2.setChecked( false );

                ivFoto.setImageResource( R.drawable.ic_launcher_foreground );

                break;

            case "Guardar":


                if(spCensos.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Debe seleccionar un censo").show();
                    break;
                }

                if(spTipoPoste.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Debe seleccionar un tipo de poste").show();
                    break;
                }

                if(spTipoCarcasa.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Debe seleccionar un tipo de carcasa").show();
                    break;
                }

                if(spTipoLampara1.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Debe seleccionar un tipo de lámpara 1").show();
                    break;
                }

                if(chkCondicionLampara2.isChecked() && spTipoLampara2.getSelectedItem() == null) {
                    new SweetAlertDialog( fragment.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Debe seleccionar un tipo de lampara2").show();
                    break;
                }






                EPoste poste = new EPoste();
                poste.Uuid = UUID.randomUUID().toString();
                poste.UuidCenso = ((ECenso) spCensos.getSelectedItem()).Uuid;

                poste.CondicionPoste = chkCondicionPoste.isChecked();

                poste.PosteId = Objects.requireNonNull( txtIdPoste.getText() ).toString();

                poste.IdTipoPoste = ((ETipoPoste) spTipoPoste.getSelectedItem()).Id;

                poste.IdTipoCarcasa = ((ETipoCarcasa) spTipoCarcasa.getSelectedItem()).Id;

                poste.CondicionLampara1 = chkCondicionLampara1.isChecked();

                poste.IdTipoLampara1 = ((ETipoLampara) spTipoLampara1.getSelectedItem()).Id;

                poste.Cantidad1 = Integer.parseInt( "0" + Objects.requireNonNull( txtCantidad1.getText() ).toString() );

                poste.Watts1 = Integer.parseInt( "0" + Objects.requireNonNull( txtWatts1.getText() ).toString() );

                poste.CargaWatts1 = Integer.parseInt( "0" + Objects.requireNonNull( txtCarga1.getText() ).toString() );

                poste.CondicionLampara2 = chkCondicionLampara2.isChecked();

                poste.IdTipoLampara2 = ((ETipoLampara) spTipoLampara2.getSelectedItem()).Id;

                poste.Cantidad2 = Integer.parseInt( "0" + Objects.requireNonNull( txtCantidad2.getText() ).toString() );

                poste.Watts2 = Integer.parseInt( "0" + txtWatts2.getText().toString() );

                poste.CargaWatts2 = Integer.parseInt( "0" + txtCarga2.getText().toString() );

                poste.EquipoAux = Integer.parseInt( "0" + txtEquipoAux.getText().toString() );

                Bitmap bitmap = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);

                poste.Foto = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                poste.GeoX = Double.parseDouble(  txtGeoX.getText().toString() );

                poste.GeoY = Double.parseDouble( txtGeoY.getText().toString() );



                if(!actualizar) {
                    new TInsertarPoste( fragment.getContext() ).execute( poste );
                    actualizar = true;
                } else {
                    new TActualizarPoste( fragment.getContext() ).execute( poste );
                }


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