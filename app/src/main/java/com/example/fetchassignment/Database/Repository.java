package com.example.fetchassignment.Database;

import android.app.Application;

import com.example.fetchassignment.DAO.ItemDAO;
import com.example.fetchassignment.Entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Repository {

    private ItemDAO mItemDAO;

    private List<Item> mAllItems;
    private List<Item> mFiftyItems;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        FetchDatabaseBuilder db  = FetchDatabaseBuilder.getDatabase(application);
        mItemDAO = db.itemDAO();

    }
    public List<Item> getAllItems(){
        databaseExecutor.execute(()->{
            mAllItems = mItemDAO.getAllItems();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllItems;
    }
    public List<Item> getFiftyItems(){
        databaseExecutor.execute(()->{
            mAllItems = mItemDAO.getFiftyItems();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mFiftyItems;
    }
    public void insert(Item item){
        databaseExecutor.execute(()->{
            mItemDAO.insert(item);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Item item){
        databaseExecutor.execute(()->{
            mItemDAO.update(item);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(Item item){
        databaseExecutor.execute(()->{
            mItemDAO.delete(item);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearAll(){
        databaseExecutor.execute(()->{
            mItemDAO.clearTable();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
