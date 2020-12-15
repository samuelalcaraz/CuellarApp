package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EPoste;

import java.util.List;


@Dao
public interface DPoste {

    @Query("SELECT * FROM  poste WHERE IdPoste = :IdPoste")
    EPoste getById(int IdPoste);

    @Query("SELECT * FROM  poste WHERE IdCenso = :IdCenso")
    EPoste getByCenso(int IdCenso);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EPoste data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(EPoste data);

}

