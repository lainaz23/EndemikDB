package com.laila.endemikdb.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.laila.endemikdb.model.Endemik;
import com.laila.endemikdb.model.Favorit;

@Database(entities = {Endemik.class, Favorit.class}, version = 1, exportSchema = false)
public abstract class EndemikDatabase extends RoomDatabase {

    private static EndemikDatabase instance;

    public abstract EndemikDao endemikDao();
    public abstract FavoritDao favoritDao();

    public static synchronized EndemikDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    EndemikDatabase.class,
                    "endemik_db"
            ).build();
        }
        return instance;
    }
}