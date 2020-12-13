package com.compuasis.censoalumbradopublico.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TipoCarcasa {

    @PrimaryKey
    public int IdTipoCarcasa;

    public String NmbreTipoCarcasa;
}
