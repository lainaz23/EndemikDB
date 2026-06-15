package com.laila.endemikdb;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.laila.endemikdb.adapter.EndemikAdapter;
import com.laila.endemikdb.database.EndemikDatabase;
import com.laila.endemikdb.model.Endemik;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rv;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pencarian");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rv = findViewById(R.id.rv_search);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        etSearch = findViewById(R.id.et_search);

        // Load semua data awal
        loadData("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData(String keyword) {
        new Thread(() -> {
            List<Endemik> list = EndemikDatabase.getInstance(this)
                    .endemikDao().search(keyword);
            runOnUiThread(() -> {
                EndemikAdapter adapter = new EndemikAdapter(list, item -> {
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