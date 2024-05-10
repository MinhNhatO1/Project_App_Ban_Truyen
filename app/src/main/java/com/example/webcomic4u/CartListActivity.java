package com.example.webcomic4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webcomic4u.Adaptor.CartListAdapter;
import com.example.webcomic4u.Helper.ManagemntCart;
import com.example.webcomic4u.Interface.ChangeNumberItemListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class CartListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagemntCart managemntCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managemntCart = new ManagemntCart(this);

        initView();

        initList();

        CalculateCart();

        bottomNavigation();
        //Check out
        TextView checkout = findViewById(R.id.thanhtoan);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this,CheckoutActivity.class));
            }
        });

        //Menu
        drawerLayout = findViewById(R.id.drawer_layoutCart);
        navigationView = findViewById(R.id.nav_viewCart);
        toolbar = findViewById(R.id.toolbarCart);
        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        Menu menu = navigationView.getMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(CartListActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_search:
                intent = new Intent(CartListActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_info:
                intent = new Intent(CartListActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                intent = new Intent(CartListActivity.this,IntroActivity.class);
                startActivity(intent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout tim_kiemBtn = findViewById(R.id.tim_kiemBtn);
        LinearLayout logout = findViewById(R.id.logoutBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
        tim_kiemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, SearchActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, IntroActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, LoginActivity.class));
            }
        });

    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt =findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt =findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartListAdapter(managemntCart.getListCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerViewList.setAdapter(adapter);
        if(managemntCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }

    private void CalculateCart() {
        double percentTax = 0.1;
        double delivery = 15;

        tax = Math.round((managemntCart.getTotalFee() * percentTax) *100)/100;
        double total = Math.round((managemntCart.getTotalFee() + tax + delivery) * 100) /100;
        double itemTotal = Math.round(managemntCart.getTotalFee() * 100) / 100;

        totalFeeTxt.setText(itemTotal + "00 VNĐ");
        taxTxt.setText(tax + "00 VNĐ");
        deliveryTxt.setText(delivery + "00 VNĐ");
        totalTxt.setText(total + "00 VNĐ");
    }
}