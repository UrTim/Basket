package com.example.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class basketAdapter extends RecyclerView.Adapter<basketAdapter.ItemsViewHolder>{

    private Context ctx;
    private List<Basket> basketList;

    public basketAdapter(Context ctx, List<Basket> basketList) {
        this.ctx = ctx;
        this.basketList = basketList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.cardlayout_forbasket,null);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Basket basketItem = basketList.get(position);

        holder.basketItemName.setText(basketItem.getItemName());
    }

    @Override
    public int getItemCount() {
        return basketList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView basketItemName;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            basketItemName = (TextView) itemView.findViewById(R.id.textViewBasketName);
        }
    }
}
