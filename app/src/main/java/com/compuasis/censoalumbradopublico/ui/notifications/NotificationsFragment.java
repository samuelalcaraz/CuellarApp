package com.compuasis.censoalumbradopublico.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;
import com.compuasis.censoalumbradopublico.tasks.TObtenerEstado;
import com.compuasis.censoalumbradopublico.tasks.TObtenerTipoPoste;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    Spinner spTipoPoste;

    NotificationsFragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.fragment = this;
        notificationsViewModel =
                ViewModelProviders.of( this ).get( NotificationsViewModel.class );
        View root = inflater.inflate( R.layout.fragment_notifications, container, false );

        spTipoPoste = root.findViewById( R.id.spTipoPoste );

        new TObtenerTipoPoste( this.getContext(), this.fragment ).execute(  );

        return root;


    }
}