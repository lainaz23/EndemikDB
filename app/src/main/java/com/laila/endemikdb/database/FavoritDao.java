package com.laila.endemikdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;
import androidx.room.Query;

import com.laila.endemikdb.model.Favorit;
import java.util.List;

@Dao
public interface FavoritDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorit favorit);

    @Delete
    void delete(Favorit favorit);

    @Query("SELECT * FROM favorit")
    List<Favorit> getAll();

    @Query("SELECT COUNT(*) FROM favorit WHERE id = :id")
    int isFavorit(String id);
}