package com.compuasis.censoalumbradopublico.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.compuasis.censoalumbradopublico.entities.EEstado;
import com.compuasis.censoalumbradopublico.entities.EMunicipio;

@Database(entities =
        {
                EEstado.class,
                EMunicipio.class
        }, version = 2)
public abstract class database extends RoomDatabase {

    public abstract DEstado dEstado();

    public  abstract  DMunicipio dMunicipio();

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