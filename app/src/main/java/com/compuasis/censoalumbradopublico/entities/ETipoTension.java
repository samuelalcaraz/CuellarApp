package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "TipoTension")
public class ETipoTension {

    @PrimaryKey
    public int Id;

    public String Nombre;

    @NotNull
    @Override
    public String toString() {
        return Nombre;
    }
}
