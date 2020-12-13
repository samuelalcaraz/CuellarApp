package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Estado")
public class EEstado {

    @PrimaryKey
    public int IdEstado;

    public String NombreEstado;

    @Override
    public String toString() {
        return NombreEstado;
    }

}
