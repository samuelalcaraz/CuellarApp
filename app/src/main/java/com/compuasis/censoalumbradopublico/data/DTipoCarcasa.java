package com.compuasis.censoalumbradopublico.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;

import java.util.List;

@Dao
public interface DTipoCarcasa {

    @Query("SELECT * FROM TipoCarcasa ORDER BY NombreTipoCarcasa")
    List<ETipoCarcasa> getAll();

    @Query("DELETE FROM TipoCarcasa")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ETipoCarcasa data);

}
