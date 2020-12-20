package com.compuasis.censoalumbradopublico.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Censo")
public class ECenso {

    @NonNull
    @PrimaryKey
    public String Uuid;

    public int IdMunicipio;

    public String Division;

    public String Zona;

    public String Agencia;

    public String Calle;

    public int IdTipoCalle;

    public String CalleMargen;

    public boolean CalleMargenIzquierda;

    public boolean CalleMargenDerecha;

    public boolean CalleMargenCentro;

    public String Manzana;

    public int IdTipoTension;

    public String EntreCalle1;

    public String EntreCalle2;

    public String PoblacionColonia;

    public String Localidad;

    public String MacAddress;

    public String getDivisionZonaAgencia() {
        return Division + ", " + Zona + ", " + Agencia;
    }

    @NotNull
    @Override
    public String toString() {
        return "[" + Uuid + "]";
    }
}
