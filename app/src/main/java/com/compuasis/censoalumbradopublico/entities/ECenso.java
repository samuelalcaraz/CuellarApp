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

    public String Zona;

    public String Agencia;

    public String Calle;

    public int IdCalleTipo;

    public String CalleMargen;

    public boolean CalleMargenIzquierda;

    public boolean CalleMargenDerecha;

    public boolean CalleMargenCentro;

    public String Manzana;

    public int IdTension;

    public String EntreCalle1;

    public String EntreCalle2;

    public String PoblacionColonia;

    public String Localidad;

    public String NombreEstado;

    public String NombreMunicipio;

    public String getEstadoMunicipio() {
        return NombreEstado + ", " + NombreMunicipio;
    }

    public String getDivisionZonaAgencia() {
        return Division + ", " + Zona + ", " + Agencia;
    }

    @NotNull
    @Override
    public String toString() {
        return "[" + IdCenso + "] " + Calle;
    }
}
