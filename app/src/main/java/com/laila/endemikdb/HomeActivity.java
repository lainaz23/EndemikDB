package com.laila.endemikdb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laila.endemikdb.fragment.HewanFragment;
import com.laila.endemikdb.fragment.TumbuhanFragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        loadFragment(new HewanFragment());

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment;
            if (item.getItemId() == R.id.nav_hewan) {
                fragment = new HewanFragment();
            } else {
                fragment = new TumbuhanFragment();
            }
            loadFragment(fragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_favorit) {
            startActivity(new Intent(this, FavoritActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_theme) {
            int currentMode = getResources().getConfiguration().uiMode
                    & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            if (currentMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                        androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                        androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
            }
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}