package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem mi = navigationView.getMenu().findItem(R.id.rain);
        onNavigationItemSelected(mi);
        navigationView.setCheckedItem(R.id.rain);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void changeFragment(Class<? extends Fragment> fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.weather_content, fragment.newInstance()).commit();
        } catch (Exception e) {
            // null
        }
    }

    private void changeFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.weather_content, fragment).commit();
        } catch (Exception e) {
            // null
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        setTitle(item.getTitle());

        switch (item.getItemId()) {
            case R.id.old:
                changeFragment(OldFragment.class);
                break;
            case R.id.sun:
                changeFragment(SunnyFragment.class);
                break;
            case R.id.cloudy:
                changeFragment(CloudyFragment.class);
                break;
            case R.id.rain:
                changeFragment(RainFragment.class);
                break;
            case R.id.snow:
                changeFragment(SnowFragment.class);
                break;
            case R.id.fog:
                changeFragment(FogFragment.class);
                break;
            default:
                Toast.makeText(this, "实现中...", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
