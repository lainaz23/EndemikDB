package com.laila.endemikdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.laila.endemikdb.model.Endemik;
import java.util.List;

@Dao
public interface EndemikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik WHERE tipe = :tipe")
    List<Endemik> getByTipe(String tipe);

    @Query("SELECT * FROM endemik WHERE nama LIKE '%' || :keyword || '%'")
    List<Endemik> search(String keyword);

    @Query("SELECT COUNT(*) FROM endemik")
    int count();

    @Query("SELECT * FROM endemik WHERE id = :id LIMIT 1")
    Endemik getById(String id);

    @Query("SELECT DISTINCT asal FROM endemik ORDER BY asal ASC")
    List<String> getAllRegion();

    @Query("SELECT * FROM endemik WHERE tipe = :tipe AND asal = :region")
    List<Endemik> getByTipeAndRegion(String tipe, String region);
}