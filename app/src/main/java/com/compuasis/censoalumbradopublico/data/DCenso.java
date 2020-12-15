package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;

import java.util.List;


@Dao
public interface DCenso {

    @Query("SELECT * FROM  censo WHERE IdCenso = :IdCenso")
    ECenso getById(int  IdCenso);

    @Query("SELECT C.IdCenso, C.IdMunicipio, C.Division, C.Zona, C.Agencia, C.Calle, C.IdCalleTipo," +
            "C.CalleMargen, C.CalleMargenIzquierda, C.CalleMargenDerecha, C.CalleMargenCentro," +
            "C.Manzana, C.IdTension, C.EntreCalle1, C.EntreCalle2, C.PoblacionColonia, C.Localidad," +
            "E.NombreEstado, M.Nombre NombreMunicipio FROM censo C " +
            "INNER JOIN municipio M ON C.IdMunicipio = M.IdMunicipio " +
            "INNER JOIN estado E on E.IdEstado = M.IdEstado ")
    List<ECenso> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ECenso censo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(ECenso censo);

}

