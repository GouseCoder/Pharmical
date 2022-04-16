package com.example.myapplication.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Fragments.AboutUsFragment;
import com.example.myapplication.Fragments.AddProductFragment;
import com.example.myapplication.Fragments.AddProductInfoFragment;
import com.example.myapplication.Fragments.AddSupplierFragment;
import com.example.myapplication.Fragments.AllSalesFragment;
import com.example.myapplication.Fragments.ContactUsFragment;
import com.example.myapplication.Fragments.DashboardFragment;
import com.example.myapplication.Fragments.ExpiringProductsFragment;
import com.example.myapplication.Fragments.ProductsFragment;
import com.example.myapplication.Fragments.ProductsNoticeFragment;
import com.example.myapplication.Fragments.SellProductFragment;
import com.example.myapplication.Fragments.SuppliersFragment;
import com.example.myapplication.Fragments.TodaySaleFragment;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        Fragment fragment = new DashboardFragment();
        setFragment(fragment);


        bottomNavigationView.setItemIconTintList(null);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Dashboard");

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment1;
            switch (id)
            {
                case R.id.miDashboard:
                    fragment1 = new DashboardFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Dashboard");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miSellProduct:
                    fragment1 = new SellProductFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Sell Product");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miSalesToday:
                    fragment1 = new TodaySaleFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Sales Today");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miSalesAll:
                    fragment1 = new AllSalesFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("All Sales");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miProducts:
                    fragment1 = new ProductsFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Products");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miProductsNotice:
                    fragment1 = new ProductsNoticeFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Less Products");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miExpiringProducts:
                    fragment1 = new ExpiringProductsFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Expiring Products");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miSellers:
                    fragment1 = new SuppliersFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Suppliers");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miAddSeller:
                    fragment1 = new AddSupplierFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Add Seller");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miAddProduct:
                    fragment1 = new AddProductFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Add Product");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miAddProductInfo:
                    fragment1 = new AddProductInfoFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Add Product Info");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miLogout:
                    FirebaseAuth.getInstance().signOut();
                    sendToLogin();
                    finish();
                    break;
                case R.id.miAboutUs:
                    fragment1 = new AboutUsFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("About Us");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.miContactUs:
                    fragment1 = new ContactUsFragment();
                    setFragment(fragment1);
                    actionBar.setTitle("Contact Us");
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Working On It", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment12;
            switch (id)
            {
                case R.id.miDashboard:
                    fragment12 = new DashboardFragment();
                    setFragment(fragment12);
                    actionBar.setTitle("Dashboard");
                    break;
                case R.id.miProducts:
                    fragment12 = new ProductsFragment();
                    setFragment(fragment12);
                    actionBar.setTitle("Products");
                    break;
                case R.id.miSellProduct:
                    fragment12 = new SellProductFragment();
                    setFragment(fragment12);
                    actionBar.setTitle("Sell Product");
                    break;
                default:
                    Toast.makeText(MainActivity.this,"Working",Toast.LENGTH_LONG).show();
            }
            return true;
        });



    }
    public void setFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }

    public void sendToLogin()
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
    }
}