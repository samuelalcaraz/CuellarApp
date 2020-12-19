package com.compuasis.censoalumbradopublico.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;

import org.jetbrains.annotations.NotNull;

import java.util.List;

// https://guides.codepath.com/android/using-the-recyclerview
public class PostesAdapter extends
        RecyclerView.Adapter<PostesAdapter.ViewHolder> {

    private final List<EPoste> mPostes;

    public PostesAdapter(List<EPoste> censos) {
        mPostes = censos;
    }

    @NotNull
    @Override
    public PostesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_poste, parent, false);

        return new ViewHolder(contactView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PostesAdapter.ViewHolder holder, int position) {

        EPoste data = mPostes.get(position);

        holder.tvLinea1.setText( String.valueOf( data.ID ) );
        holder.tvLinea2.setText( "Cantidad: " + data.Cantidad1 + "   Watts: " + data.WhatssLampara1 + "   Carga: " + data.CargaWatts1 );
        holder.tvLinea3.setText( "Cantidad: " + data.Cantidad2 + "   Watts: " + data.WhatssLampara2 + "   Carga: " + data.CargaWatts2 );

    }

    @Override
    public int getItemCount() {
        return mPostes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvLinea1, tvLinea2, tvLinea3;


        public ViewHolder(View itemView) {

            super(itemView);

            tvLinea1 = itemView.findViewById( R.id.tvLinea1);
            tvLinea2 = itemView.findViewById( R.id.tvLinea2);
            tvLinea3 = itemView.findViewById( R.id.tvLinea3);
        }
    }
}