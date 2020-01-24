package com.example.basket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class basketActivity extends AppCompatActivity {

    private static final String BASKET_URL = "http://192.168.1.53/Android/Items/basket.php";
    RecyclerView recyclerView;
    List<Basket> basketList;
    private TextView textViewBName;
    basketAdapter basketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        textViewBName = (TextView) findViewById(R.id.textViewBasketName);

        basketList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBasket);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadBasket();


    }


    public void loadBasket(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASKET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray basketItems = new JSONArray(response);
                    for (int i = 0; i < basketItems.length(); i++) {
                        JSONObject basketItemObject = basketItems.getJSONObject(i);

                        int id = basketItemObject.getInt("id_bitem");
                        String listItemName = basketItemObject.getString("bitemName");


                        Basket basketItem = new Basket(id, listItemName);
                        basketList.add(basketItem);
                    }
                    basketAdapter = new basketAdapter(basketActivity.this, basketList);
//                    itemsAdapter.setonAddListener(new ItemsAdapter.onAddListener() {
////                        @Override
////                        public void onAddClick(int position) {
////                            addToBasket();
////                        }
////                    });
                    recyclerView.setAdapter(basketAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(basketActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}

