package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "TipoCarcasa")
public class ETipoCarcasa {

    @PrimaryKey
    public int IdTipoCarcasa;

    public String NombreTipoCarcasa;

    @NotNull
    @Override
    public String toString() {
        return NombreTipoCarcasa;
    }
}
