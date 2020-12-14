package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;

import java.util.List;


@Dao
public interface DCenso {

    @Query("SELECT * FROM  censo WHERE IdCenso = :IdCenso")
    ECenso getById(int  IdCenso);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ECenso censo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(ECenso censo);

}

