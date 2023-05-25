package com.example.fetchassignment.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @PrimaryKey(autoGenerate = true)

    private int id;
    private int listId;
    private String name;





    public Item()
    {
        super();
    }
    public Item (int id, int listID, String name){

        this.id = id;
        this.listId = listID;
        this.name = name;
    }
    public int getListId() {
        return listId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
