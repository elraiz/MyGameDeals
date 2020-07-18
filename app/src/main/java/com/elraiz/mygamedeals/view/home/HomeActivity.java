package com.elraiz.mygamedeals.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.elraiz.mygamedeals.R;
import com.elraiz.mygamedeals.adapter.DealRecycleView;
import com.elraiz.mygamedeals.adapter.HotDealRecycleView;
import com.elraiz.mygamedeals.api.APIData;
import com.elraiz.mygamedeals.view.article.ArticleActivity;
import com.elraiz.mygamedeals.view.freebie.FreebieActivity;
import com.elraiz.mygamedeals.view.search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements DealRecycleView.OnItemClickListener,HotDealRecycleView.HotOnItemClickListener{
    RecyclerView recyclerView;
    RecyclerView hotrecyclerView;
    List<APIData> deals;
    List<APIData> hotdeals;
    private static String JSON_URL = "https://www.cheapshark.com/api/1.0/deals?storeID=1&sortBy=Metacritic&onSale=1";
    private static String JSON_URL_HOT = "https://www.cheapshark.com/api/1.0/deals?storeID=1&onSale=1";
    DealRecycleView dealRecycleView;
    HotDealRecycleView hotdealRecycleView;

    public static final String EXTRA_TEXT = "com.elraiz.mygamedeal.home.EXTRA_TEXT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        EditText searchGames = findViewById(R.id.home_search_game);

        searchGames.setOnEditorActionListener(editorActionListener);

        recyclerView = findViewById(R.id.dealList);
        hotrecyclerView = findViewById(R.id.hotdealList);
        hotdeals = new ArrayList<>();
        deals = new ArrayList<>();
        ParseJSON();
        ParseJSONHot();

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu home terpilih
        bottomNavigationView.setSelectedItemId(R.id.home);

        //navigasi pindah aktivitas
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.article:
                        startActivity(new Intent(getApplicationContext()
                            , ArticleActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.freebie:
                        startActivity(new Intent(getApplicationContext()
                                , FreebieActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            EditText searchGame = findViewById(R.id.home_search_game);
            String game = searchGame.getText().toString();

            Intent searchintent = new Intent(HomeActivity.this, SearchActivity.class);
            searchintent.putExtra(EXTRA_TEXT,game);
            startActivity(searchintent);
            return false;
        }
    };

    private void ParseJSON() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject gameObject = response.getJSONObject(i);

                        APIData deal = new APIData();
                        deal.setTitle(gameObject.getString("title"));
                        deal.setDealPrice(gameObject.getString("salePrice"));
                        deal.setNormalPrice(gameObject.getString("normalPrice"));
                        deal.setCoverImage(gameObject.getString("steamAppID"));
                        deal.setDiscount(gameObject.getString("savings"));
                        deal.setMetacritic(gameObject.getString("metacriticScore"));
                        deal.setSteamreview(gameObject.getString("steamRatingText"));
                        deals.add(deal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                dealRecycleView = new DealRecycleView(getApplicationContext(),deals);
                recyclerView.setAdapter(dealRecycleView);
                dealRecycleView.setOnItemClickListener(HomeActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }
    private void ParseJSONHot() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL_HOT, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < 15; i++) {
                    try {
                        JSONObject songObject = response.getJSONObject(i);

                        APIData hotdeal = new APIData();
                        hotdeal.setDealPrice(songObject.getString("salePrice"));
                        hotdeal.setNormalPrice(songObject.getString("normalPrice"));
                        hotdeal.setCoverImage(songObject.getString("steamAppID"));
                        hotdeal.setDiscount(songObject.getString("savings"));
                        hotdeals.add(hotdeal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                hotrecyclerView.setLayoutManager(manager);
                hotdealRecycleView = new HotDealRecycleView(getApplicationContext(),hotdeals);
                hotrecyclerView.setAdapter(hotdealRecycleView);
                hotdealRecycleView.setHotOnItemClickListener(HomeActivity.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(int position) {
        APIData clickedItem = deals.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }


    @Override
    public void hotonItemClick(int position) {
        APIData clickedItem = hotdeals.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }
}
