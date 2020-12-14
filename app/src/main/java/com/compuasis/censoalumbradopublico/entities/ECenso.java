package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Censo")
public class ECenso {

    @PrimaryKey(autoGenerate = true)
    public int IdCenso;

    public int IdMunicipio;

    public String Division;

    public String NombreCalle;

    public int IdCalleTipo;

    public String CalleMargen;

    public int IdCalleMargenTipo;

    public String Manzana;

    public int IdTension;

    public String EntreCalle1;

    public String EntreCalle2;

    public String PoblacionColonia;

    public String Localidad;

    @NotNull
    @Override
    public String toString() {
        return String.valueOf( IdCenso );
    }
}
