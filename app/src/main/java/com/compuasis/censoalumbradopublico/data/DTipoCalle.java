package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ETipoCalle;

import java.util.List;

@Dao
public interface DTipoCalle {

    @Query("SELECT * FROM TipoCalle ORDER BY Nombre")
    List<ETipoCalle> getAll();

    @Query("DELETE FROM TipoCalle")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ETipoCalle data);

}
