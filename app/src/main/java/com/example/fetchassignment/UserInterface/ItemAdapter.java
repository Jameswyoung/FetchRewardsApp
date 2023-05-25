package com.example.fetchassignment.UserInterface;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchassignment.Entity.Item;
import com.example.fetchassignment.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemItemView;
        private ItemViewHolder (View itemview){

            super(itemview);

            itemItemView = itemview.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Item current = mItems.get(position);
                    Intent intent = new Intent(context, ItemListScreen.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("listID", current.getListId());
                    intent.putExtra("name", current.getName());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Item> mItems;
    private final Context context;
    private final LayoutInflater mInflater;

    public ItemAdapter (Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_item_view_item, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {

        if (mItems != null){
            Item current = mItems.get(position);
            String id = String.valueOf(current.getId());
            String listID = String.valueOf(current.getId());
            String name = current.getName();
            holder.itemItemView.setText("ID: " + id + "\n" + "LIST ID: " + listID +"\n" +  "NAME: " + name+ "\n");

        }else{
            holder.itemItemView.setText("No Item Title");
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }
}
