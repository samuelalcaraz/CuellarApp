package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;

import java.util.List;

@Dao
public interface DTipoPoste {

    @Query("SELECT * FROM TipoPoste ORDER BY NombreTipoPoste")
    List<ETipoPoste> getAll();

    @Query("DELETE FROM TipoPoste")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ETipoPoste data);

}
