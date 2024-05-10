package com.example.webcomic4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.webcomic4u.Domain.MangaDomain;
import com.example.webcomic4u.Helper.ManagemntCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class ShowDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private TextView addToCartBtn;
private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt;
private ImageView plusBtn, minusBtn, picManga, cart_detail;
private MangaDomain object;
int numberOrder = 1;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
private ManagemntCart managemntCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managemntCart = new ManagemntCart(this);

        initView();
        getBundle();

        ConstraintLayout constraintLayout =findViewById(R.id.showDetail);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //navigation drawer
        drawerLayout = findViewById(R.id.drawer_layoutShowDetail);
        navigationView = findViewById(R.id.nav_viewDetailCart);
        toolbar = findViewById(R.id.toolbarDetailCart);
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
                Intent intent = new Intent(ShowDetailActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_cart:
                intent = new Intent(ShowDetailActivity.this,CartListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                intent = new Intent(ShowDetailActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_info:
                intent = new Intent(ShowDetailActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(ShowDetailActivity.this,IntroActivity.class);
                startActivity(intent);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getBundle() {
        object = (MangaDomain) getIntent().getSerializableExtra("object");



        int drawableResourceId = this.getResources().getIdentifier(object.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picManga);
        titleTxt.setText(object.getTitle());
        feeTxt.setText(object.getFee()+" VNĐ/quyển");
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCart(numberOrder);
                managemntCart.insertManga(object);
            }
        });

        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailActivity.this,MainActivity.class));
            }
        });
        cart_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowDetailActivity.this,CartListActivity.class));
            }
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picManga = findViewById(R.id.picmanga);
        cart_detail = findViewById(R.id.cart_detail);
    }
}