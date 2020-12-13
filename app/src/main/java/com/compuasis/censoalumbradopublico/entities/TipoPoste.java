package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipoPoste {

    @PrimaryKey
    public int IdTipoPoste;

    public String NombreTipoPoste;
}
