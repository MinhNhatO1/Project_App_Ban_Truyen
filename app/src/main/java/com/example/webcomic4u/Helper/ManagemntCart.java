package com.example.webcomic4u.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.webcomic4u.Domain.MangaDomain;
import com.example.webcomic4u.Interface.ChangeNumberItemListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ManagemntCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagemntCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertManga(MangaDomain item) {
        ArrayList<MangaDomain> listManga = getListCart();
        boolean existAlready = false;
        int n=0;
        for(int i = 0; i< listManga.size(); i++) {
            if(listManga.get(i).getTitle().equals(item.getTitle())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if(existAlready) {
            listManga.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listManga.add(item);
        }
        tinyDB.putListObject("CartList",listManga);
        Toast.makeText(context, "Đã thêm vào giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<MangaDomain> getListCart(){
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberManga(ArrayList<MangaDomain> listManga, int position, ChangeNumberItemListener changeNumberItemListener) {
        listManga.get(position).setNumberInCart(listManga.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listManga);
        changeNumberItemListener.changed();
    }
    public void minusNumberManga(ArrayList<MangaDomain> listManga, int position, ChangeNumberItemListener changeNumberItemListener) {
        if (listManga.get(position).getNumberInCart()==1){
            listManga.remove(position);

        }else {
            listManga.get(position).setNumberInCart(listManga.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList",listManga);
        changeNumberItemListener.changed();

    }
    public Double getTotalFee() {
        ArrayList<MangaDomain> listManga = getListCart();
        double fee = 0;
        for (int i =0; i<listManga.size(); i++) {
            fee = fee + (listManga.get(i).getFee() * listManga.get(i).getNumberInCart());

        }
        return fee;
    }
}
