package com.compuasis.censoalumbradopublico.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.compuasis.censoalumbradopublico.entities.ECenso;
import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;
import com.compuasis.censoalumbradopublico.entities.EPoste;
import com.compuasis.censoalumbradopublico.entities.ETipoCarcasa;
import com.compuasis.censoalumbradopublico.entities.ETipoLampara;
import com.compuasis.censoalumbradopublico.entities.ETipoPoste;

@Database(entities =
        {
                EEstado.class,
                EMunicipio.class,
                ECenso.class,
                EPoste.class,
                ETipoPoste.class,
                ETipoCarcasa.class,
                ETipoLampara.class
        }, version = 9)
public abstract class database extends RoomDatabase {

    public abstract DEstado dEstado();

    public abstract DMunicipio dMunicipio();

    public abstract  DCenso dCenso();

    public abstract DPoste dPoste();

    public abstract DTipoPoste dTipoPoste();

    public abstract DTipoCarcasa dTipoCarcasa();

    public abstract DTipoLampara dTipoLampara();

    private static volatile database INSTANCE;

    public static database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                            database.class, "censoap-db" )
                            .fallbackToDestructiveMigration()
                            .addCallback( sRoomDatabaseCallback )
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen( db );

        }
    };
}