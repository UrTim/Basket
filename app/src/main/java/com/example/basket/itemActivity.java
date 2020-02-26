package com.example.basket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class itemActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private final static String ITEMS_URL = "http://192.168.1.53/Android/Items/items.php";
    private final static String ADDTOBASKET_URL = "http://192.168.1.53/Android/Items/postadd.php";
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;
    List<Items> itemsList;
    private TextView textViewName;
    private final static String TAG = "my app";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        textViewName = (TextView) findViewById(R.id.textViewName);

        itemsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewitem);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadItems();
    }

    private void loadItems() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ITEMS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray items = new JSONArray(response);
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject itemObject = items.getJSONObject(i);

                        int id = itemObject.getInt("id");
                        String listItemName = itemObject.getString("itemName");


                        Items item = new Items(id, listItemName);
                        itemsList.add(item);
                    }
                    itemsAdapter = new ItemsAdapter(itemActivity.this, itemsList);
                    recyclerView.setAdapter(itemsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(itemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

//    public void addToBasket() {
////        Log.d(TAG, "addToBasket: adding");
////
////        final String itemName = textViewName.getText().toString();
////
////        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDTOBASKET_URL, new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(itemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        }) {
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> params = new HashMap<>();
////                params.put("itemName", itemName);
////                return params;
////            }
////        };
////
////        RequestQueue requestQueue = Volley.newRequestQueue(this);
////        requestQueue.add(stringRequest);
////    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Items> newList = new ArrayList<>();
        for (Items item : itemsList) {
            if (item.getListName().toLowerCase().contains(userInput)) {
                newList.add(item);
            }
        }
        itemsAdapter.updateList(newList);
        return true;
    }

}
