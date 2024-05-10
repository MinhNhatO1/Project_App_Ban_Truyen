package com.example.webcomic4u.Adaptor;

import android.annotation.SuppressLint;
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
import com.example.webcomic4u.Helper.ManagemntCart;
import com.example.webcomic4u.Interface.ChangeNumberItemListener;
import com.example.webcomic4u.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<MangaDomain> mangaDomains;
    private ManagemntCart managemntCart;
    private ChangeNumberItemListener changeNumberItemListener;

    public CartListAdapter(ArrayList<MangaDomain> mangaDomains, Context context, ChangeNumberItemListener changeNumberItemListener) {
        this.mangaDomains = mangaDomains;
        this.managemntCart = new ManagemntCart(context);
        this.changeNumberItemListener = changeNumberItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mangaDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(mangaDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((mangaDomains.get(position).getNumberInCart() * mangaDomains.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(mangaDomains.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(mangaDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managemntCart.plusNumberManga(mangaDomains, position, new ChangeNumberItemListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemListener.changed();
                    }
                });

            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managemntCart.minusNumberManga(mangaDomains, position, new ChangeNumberItemListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemListener.changed();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mangaDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
