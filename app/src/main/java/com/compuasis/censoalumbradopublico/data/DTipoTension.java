package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ETipoTension;

import java.util.List;

@Dao
public interface DTipoTension {

    @Query("SELECT * FROM TipoTension ORDER BY Nombre")
    List<ETipoTension> getAll();

    @Query("DELETE FROM TipoTension")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ETipoTension data);

}
