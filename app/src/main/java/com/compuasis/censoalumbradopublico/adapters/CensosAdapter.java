package com.compuasis.censoalumbradopublico.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.compuasis.censoalumbradopublico.R;
import com.compuasis.censoalumbradopublico.entities.ECensoPoste;

import org.jetbrains.annotations.NotNull;

import java.util.List;
// https://guides.codepath.com/android/using-the-recyclerview
public class CensosAdapter extends
        RecyclerView.Adapter<CensosAdapter.ViewHolder> {

    private final List<ECensoPoste> mCensos;

    public CensosAdapter(List<ECensoPoste> censos) {
        mCensos = censos;
    }

    @NotNull
    @Override
    public CensosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_censo, parent, false);

        return new ViewHolder( contactView );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CensosAdapter.ViewHolder holder, int position) {

        ECensoPoste data = mCensos.get(position);

        holder.tvEstadoMunicipio.setText( data.getEstadoMunicipio() );
        holder.tvDivisionZonaAgencia.setText( data.Censo.getDivisionZonaAgencia() );
        holder.tvCalle.setText( data.Censo.Calle + ", " + data.Censo.PoblacionColonia );

    }

    @Override
    public int getItemCount() {
        return mCensos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvEstadoMunicipio, tvDivisionZonaAgencia, tvCalle;

        public ViewHolder(View itemView) {

            super(itemView);

            tvEstadoMunicipio = itemView.findViewById( R.id.tvEstadoMunicipio);
            tvDivisionZonaAgencia= itemView.findViewById( R.id.tvDivisionZonaAgencia);
            tvCalle= itemView.findViewById( R.id.tvCalle);
        }
    }
}