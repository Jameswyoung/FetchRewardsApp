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

    @Query("SELECT * FROM items ORDER BY listId, name")
    //ORDER BY listID ASC
    List<Item> getAllItems();
    @Query("SELECT * FROM items WHERE name <> 'null' AND name <> '' AND name <> 'Item 0' ORDER BY listId, name" )
    List<Item> getFilteredItems();
    //SQLite Room databases do not have an option to enter parameters for database queries so four different search methods had to be created.
    @Query("SELECT * FROM items WHERE listId = 4 AND name <> 'null' AND name <> '' AND name <> 'Item 0' ORDER BY listId, name" )
    List<Item> getListID4Items();
    @Query("SELECT * FROM items WHERE listId = 3 AND name <> 'null' AND name <> '' AND name <> 'Item 0' ORDER BY listId, name" )
    List<Item> getListID3Items();
    @Query("SELECT * FROM items WHERE listId = 2 AND name <> 'null' AND name <> '' AND name <> 'Item 0' ORDER BY listId, name" )
    List<Item> getListID2Items();
    @Query("SELECT * FROM items WHERE listId = 1 AND name <> 'null' AND name <> '' AND name <> 'Item 0' ORDER BY listId, name" )
    List<Item> getListID1Items();

    @Query("DELETE FROM items WHERE 1=1")
    void clearTable();


}
