package com.compuasis.censoalumbradopublico.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.DialogFragment;

import com.compuasis.censoalumbradopublico.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MultipleChioceDialogFragment extends DialogFragment {

    public interface onMultipleChioceListener extends Serializable {
        void onPossitiveButtonClicked(String[] list, ArrayList<String> selectedItems);
        void onNegativeButtonClicked();
    }

    onMultipleChioceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );
        try {
            mListener = (onMultipleChioceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+ "onMultipleChioceListener");
        } finally {
        }
    }

    ArrayList<String> selectedItems = new ArrayList<>();

    public static MultipleChioceDialogFragment getInstance(onMultipleChioceListener dialogInterface) {
        MultipleChioceDialogFragment fragmentDialog = new MultipleChioceDialogFragment();

        // set fragment arguments
        Bundle args = new Bundle();
        args.putSerializable("dialogInterface", dialogInterface);
        fragmentDialog.setArguments(args);

        return fragmentDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder( getActivity() );

        final String[] list = getActivity().getResources().getStringArray( R.array.calleMargen );

        dialog.setTitle( "Calle margen")
                .setMultiChoiceItems( list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if(isChecked) {
                            selectedItems.add( list[which] );
                        } else {
                            selectedItems.remove( list[which] );
                        }

                    }
                } )
                .setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPossitiveButtonClicked( list, selectedItems );
                    }
                } )
                .setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onNegativeButtonClicked();
                    }
                } );

        return dialog.create();
    }
}
