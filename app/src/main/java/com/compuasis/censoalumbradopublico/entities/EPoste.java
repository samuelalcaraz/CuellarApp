package com.compuasis.censoalumbradopublico.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Poste")
public class EPoste {

    @PrimaryKey(autoGenerate = true)
    public int IdPoste;

    public int IdCenso;

    public boolean CondicionPoste;

    public String ID;

    public int IdTipoPoste;

    public int IdTipoCarcasa;

    public boolean CondicionLampara1;

    public int IdTipoLampara1;

    public int Cantidad1;

    public int Watts1;

    public int CargaWatts1;

    public boolean CondicionLampara2;

    public int IdTipoLampara2;

    public int Cantidad2;

    public int Watts2;

    public int CargaWatts2;

    public int EquipoAux;

    public byte[] Foto;

    public double GeoX;

    public double GeoY;

    @NonNull
    @Override
    public String toString() {
        return ID;
    }
}
