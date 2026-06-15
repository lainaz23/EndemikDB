package com.laila.endemikdb;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.laila.endemikdb.adapter.FavoritAdapter;
import com.laila.endemikdb.database.EndemikDatabase;
import com.laila.endemikdb.model.Favorit;
import java.util.List;

public class FavoritActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorit");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView rv = findViewById(R.id.rv_favorit);
        rv.setLayoutManager(new GridLayoutManager(this, 2));

        new Thread(() -> {
            List<Favorit> list = EndemikDatabase.getInstance(this).favoritDao().getAll();
            runOnUiThread(() -> {
                FavoritAdapter adapter = new FavoritAdapter(list, item -> {
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("id", item.getId());
                    startActivity(intent);
                });
                rv.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}