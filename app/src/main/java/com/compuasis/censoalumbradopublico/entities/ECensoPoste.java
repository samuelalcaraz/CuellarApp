package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ECensoPoste {

    @Embedded
    public ECenso Censo;

    @Relation( parentColumn = "Uuid", entityColumn = "UuidCenso")
    public List<EPoste> Poste;

    @Relation(
            parentColumn = "IdMunicipio",
            entityColumn = "Id"
    )
    public EEstado Estado;

    @Relation( parentColumn = "IdMunicipio", entityColumn = "IdEstado")
    public EMunicipio Municipio;

    public String getEstadoMunicipio() {
        return Estado.Nombre + ", " + Municipio.Nombre;
    }

}
