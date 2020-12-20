package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.compuasis.censoalumbradopublico.entities.EPoste;

import java.util.List;


@Dao
public interface DPoste {


    @Query("SELECT * FROM  poste WHERE Uuid = :Uuid")
    List<EPoste> getByCenso(String Uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EPoste data);

    @Update
    void update(EPoste data);

}

