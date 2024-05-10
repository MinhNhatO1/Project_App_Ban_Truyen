package com.example.webcomic4u.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.webcomic4u.Domain.MangaDomain;
import com.example.webcomic4u.Interface.RecyclerViewInterface;
import com.example.webcomic4u.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    Context context;

    private List<MangaDomain> itemList;

    private final RecyclerViewInterface recyclerViewInterface;

    public ItemAdapter(Context context,List<MangaDomain> itemList,RecyclerViewInterface recyclerViewInterface) {

        this.itemList = itemList;
        this.recyclerViewInterface = recyclerViewInterface;
;

    }
    public void setFilteredList(List<MangaDomain> filteredList){
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_item,parent,false);
        return new ItemHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemHolder holder, int position) {
        MangaDomain item = itemList.get(position);
        holder.name.setText(item.getTitle());
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(itemList.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        if (itemList == null){
            return 0;
        } else {
            return itemList.size();
        }
    }


    public static class ItemHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img;


        public ItemHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            name = itemView.findViewById(R.id.txt);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }
}
