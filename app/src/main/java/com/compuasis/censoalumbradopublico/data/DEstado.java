package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.EEstado;

import java.util.List;
@Dao
public interface DEstado {

    @Query("SELECT * FROM estado")
    List<EEstado> getAll();

    @Query("DELETE FROM estado")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EEstado estado);

}
