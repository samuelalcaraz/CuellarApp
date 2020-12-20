package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.ECensoPoste;

import java.util.List;


@Dao
public interface DCenso {

    @Query("SELECT * FROM  censo WHERE Uuid = :Uuid")
    ECenso getById(int  Uuid);

    @Transaction
    @Query("SELECT C.Uuid, C.IdMunicipio, C.Division, C.Zona, C.Agencia, C.Calle, C.IdTipoCalle," +
            "C.CalleMargen, C.CalleMargenIzquierda, C.CalleMargenDerecha, C.CalleMargenCentro," +
            "C.Manzana, C.IdTipoTension, C.EntreCalle1, C.EntreCalle2, C.PoblacionColonia, C.Localidad," +
            "E.Nombre, M.Nombre FROM censo C " +
            "INNER JOIN municipio M ON C.IdMunicipio = M.Id " +
            "INNER JOIN estado E on E.Id = M.IdEstado ")
    List<ECenso> getAll();

    @Transaction
    @Query("SELECT C.Uuid, C.IdMunicipio, C.Division, C.Zona, C.Agencia, C.Calle, C.IdTipoCalle," +
            "C.CalleMargen, C.CalleMargenIzquierda, C.CalleMargenDerecha, C.CalleMargenCentro," +
            "C.Manzana, C.IdTipoTension, C.EntreCalle1, C.EntreCalle2, C.PoblacionColonia, C.Localidad," +
            "E.Nombre, M.Nombre, " +
            "P.CondicionPoste, P.PosteId, P.IdTipoPoste, P.IdTipoPoste, P.IdTipoCarcasa, " +
            "P.CondicionLampara1, P.IdTipoLampara1, P.Cantidad1, P.Watts1, P.CargaWatts1, " +
            "P.CondicionLampara2, P.IdTipoLampara2, P.Cantidad2, P.Watts2, P.CargaWatts2, " +
            "P.EquipoAux, P.Foto, P.GeoX, P.GeoY " +
            "FROM Censo C " +
            "INNER JOIN Municipio M ON C.IdMunicipio = M.Id " +
            "INNER JOIN Estado E on E.Id = M.IdEstado " +
            "LEFT JOIN Poste P on P.Uuid = C.Uuid")
    List<ECensoPoste> getAllInfo();

    @Transaction
    @Query("SELECT * FROM Censo")
    List<ECensoPoste> getCensoPoste();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ECenso censo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ECenso censo);

}

