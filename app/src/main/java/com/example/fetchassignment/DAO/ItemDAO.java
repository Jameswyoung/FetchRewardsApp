package com.example.fetchassignment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fetchassignment.Entity.Item;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items ORDER BY id")
    //ORDER BY listID ASC
    List<Item> getAllItems();
 @Query("SELECT * FROM items WHERE id > 1 AND id< 50 ORDER BY listID ASC")
 List<Item> getFiftyItems();

    @Query("DELETE FROM items WHERE 1=1")
    void clearTable();


}
