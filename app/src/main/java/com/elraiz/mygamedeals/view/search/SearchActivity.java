package com.elraiz.mygamedeals.view.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.elraiz.mygamedeals.R;
import com.elraiz.mygamedeals.adapter.SearchRecycleView;
import com.elraiz.mygamedeals.api.APIData;
import com.elraiz.mygamedeals.view.article.ArticleActivity;
import com.elraiz.mygamedeals.view.freebie.FreebieActivity;
import com.elraiz.mygamedeals.view.home.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchRecycleView.OnItemClickListener {
    RecyclerView searchrecyclerView;
    List<APIData> searches;
    SearchRecycleView searchRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent searchintent = getIntent();
        String game = searchintent.getStringExtra(HomeActivity.EXTRA_TEXT);

        TextView gametext = findViewById(R.id.search_game_title);
        gametext.setText(game);

        searchrecyclerView = findViewById(R.id.searchList);
        searches = new ArrayList<>();
        ParseJSON();

        //inisialisasi variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //menu search terpilih
        bottomNavigationView.setSelectedItemId(R.id.home);

        //navigasi pindah aktivitas
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0,0);
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

    private void ParseJSON() {
        Intent searchintent = getIntent();
        String gamesearch = searchintent.getStringExtra(HomeActivity.EXTRA_TEXT);
        RequestQueue queue = Volley.newRequestQueue(this);
        gamesearch = gamesearch.replaceAll(" ","%20");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://www.cheapshark.com/api/1.0/deals?storeID=1&title="+gamesearch, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject gameObject = response.getJSONObject(i);

                        APIData search = new APIData();
                        search.setTitle(gameObject.getString("title"));
                        search.setDealPrice(gameObject.getString("salePrice"));
                        search.setNormalPrice(gameObject.getString("normalPrice"));
                        search.setCoverImage(gameObject.getString("steamAppID"));
                        search.setDiscount(gameObject.getString("savings"));
                        search.setMetacritic(gameObject.getString("steamRatingPercent"));
                        search.setSteamreview(gameObject.getString("steamRatingText"));
                        searches.add(search);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                searchrecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                searchRecycleView = new SearchRecycleView(getApplicationContext(),searches);
                searchrecyclerView.setAdapter(searchRecycleView);
                searchRecycleView.setOnItemClickListener(SearchActivity.this);
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
        APIData clickedItem = searches.get(position);
        Intent intentstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.steampowered.com/app/"+clickedItem.getCoverImage()));
        startActivity(intentstore);
    }

}
