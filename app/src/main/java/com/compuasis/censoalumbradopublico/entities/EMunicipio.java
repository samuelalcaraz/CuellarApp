package com.compuasis.censoalumbradopublico.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Municipio")
//        indices = {@Index(value = {"IdEstado"})})
public class EMunicipio {

    @PrimaryKey
    public int Id;

    public int ClaveMunicipio;

    public String Nombre;


    public int IdEstado;

    @NonNull
    @Override
    public String toString() {
        return Nombre;
    }
}
