package com.example.fetchassignment.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.*;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AsyncCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchassignment.Database.Repository;
import com.example.fetchassignment.Entity.Item;
import com.example.fetchassignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;


public class ItemListScreen extends AppCompatActivity {


    private int size;
    private Repository repository;
    private RecyclerView itemRV;
    private ProgressBar progressBar;
    private ItemAdapter adapter;
    private List<Item> items;

    String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());


        repository = new Repository(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_screen);


        itemRV = findViewById(R.id.itemListRecyclerView);
        progressBar = findViewById(R.id.progressBar1);

        RequestQueue queue1 = Volley.newRequestQueue(ItemListScreen.this);
        queue = queue1;

        final ItemAdapter itemAdapter = new ItemAdapter(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView  = findViewById(R.id.itemListRecyclerView);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(repository.getAllItems().isEmpty()) {
            //moves the download of the json file offof the UI thread
            startAsyncTask();
        }
        //fills GUI table with database data

        items = repository.getAllItems();
        itemAdapter.setItems(items);
    }
    private void getData () throws InterruptedException {
        //fills database with JSON objects
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject responseObj = response.getJSONObject(i);
                        int id = responseObj.getInt("id");
                        int listId = responseObj.getInt("listId");
                        String name = responseObj.getString("name");
                        Item item = new Item(id, listId, name);
                        repository.insert(item);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItemListScreen.this, "Failed to get the data", Toast.LENGTH_SHORT).show();
                    }
                });
        //Thread.sleep(1000);
            queue.add(jsonArrayRequest);
    }

    public void startAsyncTask(){
        ItemsAsyncTask itemsAsyncTask = new ItemsAsyncTask(this);
        //not sure what this integerparameter does but a youtube video said this was very important to have.
        itemsAsyncTask.execute(10);
    }
    private static class ItemsAsyncTask extends AsyncTask<Integer, Integer, String>{

        private WeakReference<ItemListScreen> reference;

        ItemsAsyncTask(ItemListScreen activity){
            reference = new WeakReference<ItemListScreen>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ItemListScreen activity = reference.get();
            if (activity == null || activity.isFinishing()){
            return;
            }
            //This should begin the progress bar on the screen, which should continue throughout the doInBackground method.
            activity.itemRV.setVisibility(View.INVISIBLE);
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            //this calls the getData method that performs the data transfer away from the UI thread.
            try {
                ItemListScreen activity = reference.get();
                if (activity == null || activity.isFinishing()){
                    return "return";
                }
                activity.getData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "return";        }



        @Override
        protected void onPostExecute(String s) {
            //After the doInBackground method is complete, this should remove the progress bar
            // and then continue on to the rest of the OnCreate method where the GUI table will be filled witht he database objects
            super.onPostExecute(s);
            ItemListScreen activity = reference.get();
            if (activity == null || activity.isFinishing()){
                return;
            }
            activity.itemRV.setVisibility(View.VISIBLE);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
