package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "TipoPoste")
public class ETipoPoste {

    @PrimaryKey
    public int IdTipoPoste;

    public String NombreTipoPoste;

    @NotNull
    @Override
    public String toString() {
        return NombreTipoPoste;
    }
}
