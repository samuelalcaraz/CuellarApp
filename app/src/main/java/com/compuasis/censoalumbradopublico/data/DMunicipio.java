package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.EMunicipio;

import java.util.List;

@Dao
public interface DMunicipio {

    @Query("SELECT * FROM municipio WHERE IdEstado = :idEstado")
    List<EMunicipio> getByState(int idEstado);

    @Query("DELETE FROM municipio")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EMunicipio municipio);

}
