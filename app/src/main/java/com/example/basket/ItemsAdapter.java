package com.example.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{

    private Context ctx;
    private List<Items> itemsList;
    private final static String ADDTOBASKET_URL = "http://192.168.1.53/Android/Items/postadd.php";


    public ItemsAdapter(Context ctx, List<Items> itemsList) {
        this.ctx = ctx;
        this.itemsList = itemsList;
    }


    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_layout,null);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Items item = itemsList.get(position);
        holder.textViewName.setText(item.getListName());
        holder.textViewID.setText("" + item.getId());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        
        TextView textViewName;
        TextView textViewID;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewID = (TextView) itemView.findViewById(R.id.textViewItemID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Toast toast = Toast.makeText(ctx,"Item " + position + " clicked!", Toast.LENGTH_SHORT);
                    toast.show();
                    addToBasket();
                }
            });

        }

        public void addToBasket() {

            
        final String itemName = textViewName.getText().toString();
        final  String itemID = textViewID.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDTOBASKET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   JSONObject jsonObject = new JSONObject(response);
                   Toast.makeText(ctx, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx , error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", itemID);
                params.put("itemName", itemName);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);
    }

    }

    public void updateList(List<Items> newList){
        itemsList = new ArrayList<>();
        itemsList.addAll(newList);
        notifyDataSetChanged();

    }


}
