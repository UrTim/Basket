package com.example.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>{

    private Context ctx;
    private List<Items> itemsList;
    private onAddListener monAddListner;


    public ItemsAdapter(Context ctx, List<Items> itemsList) {
        this.ctx = ctx;
        this.itemsList = itemsList;
    }

    public void setonAddListener(onAddListener listener){
        monAddListner = listener;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.card_layout,null);
        return new ItemsViewHolder(view,monAddListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        Items item = itemsList.get(position);
        holder.textViewName.setText(item.getListName());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        Button buttonAdd;

        public ItemsViewHolder(@NonNull View itemView, final onAddListener onAddListener) {
            super(itemView);


            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            buttonAdd = (Button) itemView.findViewById(R.id.buttonAdd);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onAddListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onAddListener.onAddClick(position);
                        }
                    }
                }
            });

        }

    }
    public void updateList(List<Items> newList){
        itemsList = new ArrayList<>();
        itemsList.addAll(newList);
        notifyDataSetChanged();

    }

    public interface onAddListener{
        void onAddClick(int position);
    }
}
