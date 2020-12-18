package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ETipoLampara;

import java.util.List;

@Dao
public interface DTipoLampara {

    @Query("SELECT * FROM TipoLampara ORDER BY NombreTipoLampara")
    List<ETipoLampara> getAll();

    @Query("DELETE FROM TipoLampara")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ETipoLampara data);

}
