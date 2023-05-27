package com.example.fetchassignment.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.*;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchassignment.Database.Repository;
import com.example.fetchassignment.Entity.Item;
import com.example.fetchassignment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


public class ItemListScreen extends AppCompatActivity {


    private int size;
    private Repository repository;
    private RecyclerView itemRV;
    private ProgressBar progressBar;
    private ItemAdapter adapter;
    private List<Item> items;
    JSONArray response1;
    GifImageView runningDog;
    TextView dogText;
    TextView pleaseTxt;

    String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new Repository(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_screen);


        itemRV = findViewById(R.id.itemRecycler);
        progressBar = findViewById(R.id.progressBar1);
        runningDog = findViewById(R.id.runningDog);
        dogText = findViewById(R.id.dogText);
        ImageButton sBttn = findViewById(R.id.searchBttn);
        EditText searchTxt = findViewById(R.id.searchTxt);
        pleaseTxt = findViewById(R.id.pleaseTxt);

        RequestQueue queue1 = Volley.newRequestQueue(ItemListScreen.this);
        queue = queue1;
        final ItemAdapter itemAdapter = new ItemAdapter(this);
        adapter = itemAdapter;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView  = findViewById(R.id.itemRecycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchList(searchTxt.getText().toString());
            }
        });

//Ensures that all items have been loaded into the database, if the database is not full, the JSON download will occur.
        if(repository.getAllItems().size() < 999) {
            try {
                fetchData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        items = repository.getFilteredItems();
        int count = repository.getFilteredItems().size();
        System.out.println(count);
        adapter.setItems(items);
    }

    //This method launches the Volley request that connects to the JSON url.
    private void fetchData () throws InterruptedException {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 response1 = response;
                startAsyncTask();
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ItemListScreen.this, "Failed to get the data", Toast.LENGTH_SHORT).show();
                    }
                });
            queue.add(jsonArrayRequest);
    }
//AsyncTask is used to move the download off of the UI thread.
    public void startAsyncTask(){
        ItemsAsyncTask itemsAsyncTask = new ItemsAsyncTask(this);
        itemsAsyncTask.execute(10);
    }
    private static class ItemsAsyncTask extends AsyncTask<Integer, Integer, String>{

        private WeakReference<ItemListScreen> reference;

        ItemsAsyncTask(ItemListScreen activity){
            reference = new WeakReference<ItemListScreen>(activity);
        }
        @Override
        protected void onPreExecute() {
            //Before the download starts, the running dog gif is set to visible.
            super.onPreExecute();
            ItemListScreen activity = reference.get();
            if (activity == null || activity.isFinishing()){
            return;
            }
            activity.itemRV.setVisibility(View.INVISIBLE);
            activity.runningDog.setVisibility(View.VISIBLE);
            activity.dogText.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            //this converts the JSON Array objects into Item objects and stores them in a Room database.
                ItemListScreen activity = reference.get();
                if (activity == null || activity.isFinishing()){
                    return "return";
                }
                for (int i = 0; i < activity.response1.length(); i++) {
                    try {
                        JSONObject responseObj = activity.response1.getJSONObject(i);
                        int id = responseObj.getInt("id");
                        int listId = responseObj.getInt("listId");
                        String name = responseObj.getString("name");
                        Item item = new Item(id, listId, name);
                        activity.repository.insert(item);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            return "return";        }



        @Override
        protected void onPostExecute(String s) {
            //Once the download is complete the running dog gif is set to invisible and the RecyclerView is filled with filtered items from the database.
            super.onPostExecute(s);
            ItemListScreen activity = reference.get();
            if (activity == null || activity.isFinishing()){
                return;
            }
            activity.itemRV.setVisibility(View.VISIBLE);
            activity.runningDog.setVisibility(View.INVISIBLE);
            activity.dogText.setVisibility(View.INVISIBLE);

            activity.items = activity.repository.getFilteredItems();
            activity.adapter.setItems(activity.items);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //This method is connected to the search field. The method takes whatever number is entered and then filters the database items depending on that number.
    public void searchList(String listID){
        boolean found = false;
        switch (listID){
            case "1": listID.equals("1");
                items = repository.getListID1Items();
                adapter.setItems(items);
                found = true;
                break;
            case "2": listID.equals("2");
                items = repository.getListID2Items();
                adapter.setItems(items);
                found = true;
                break;
            case "3": listID.equals("3");
                items = repository.getListID3Items();
                adapter.setItems(items);
                found = true;
                break;
            case "4": listID.equals("4");
                items = repository.getListID4Items();
                adapter.setItems(items);
                found = true;
                break;
                case "": listID.equals("");
                items = repository.getFilteredItems();
                adapter.setItems(items);
                    found = true;
                    break;

        }
        if(found == false){
            pleaseTxt.setText("*Please enter 1 - 4*");


            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(150); //You can manage the blinking time with this parameter
            anim.setStartOffset(0);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            pleaseTxt.startAnimation(anim);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pleaseTxt.setText("");
                }
            }, 2500);
        }
    }
}
