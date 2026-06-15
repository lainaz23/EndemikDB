package com.laila.endemikdb;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.laila.endemikdb.database.EndemikDatabase;
import com.laila.endemikdb.model.Endemik;
import com.laila.endemikdb.model.Favorit;

public class DetailActivity extends AppCompatActivity {

    Endemik endemik;
    boolean isFavorit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Tombol back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String id = getIntent().getStringExtra("id");

        new Thread(() -> {
            EndemikDatabase db = EndemikDatabase.getInstance(this);

            // Ambil data endemik by id
            endemik = db.endemikDao().getById(id);
            isFavorit = db.favoritDao().isFavorit(id) > 0;

            runOnUiThread(() -> {
                if (endemik != null) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(endemik.getNama());
                    }

                    ImageView ivFoto = findViewById(R.id.iv_foto);
                    TextView tvNama = findViewById(R.id.tv_nama);
                    TextView tvNamaLatin = findViewById(R.id.tv_nama_latin);
                    TextView tvAsal = findViewById(R.id.tv_asal);
                    TextView tvStatus = findViewById(R.id.tv_status);
                    TextView tvDeskripsi = findViewById(R.id.tv_deskripsi);

                    tvNama.setText(endemik.getNama());
                    tvNamaLatin.setText(endemik.getNamaLatin());
                    tvAsal.setText("Asal: " + endemik.getAsal());
                    tvStatus.setText(endemik.getStatus());
                    tvDeskripsi.setText(endemik.getDeskripsi());

                    Glide.with(DetailActivity.this).load(endemik.getFoto()).into(ivFoto);
                    invalidateOptionsMenu();
                }
            });
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_favorit);
        if (item != null) {
            item.setIcon(isFavorit ?
                    R.drawable.ic_favorite_filled :
                    R.drawable.ic_favorite);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_favorit) {
            toggleFavorit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleFavorit() {
        new Thread(() -> {
            EndemikDatabase db = EndemikDatabase.getInstance(this);
            if (isFavorit) {
                Favorit f = new Favorit();
                f.setId(endemik.getId());
                db.favoritDao().delete(f);
                isFavorit = false;
                runOnUiThread(() -> Toast.makeText(this, "Dihapus dari favorit", Toast.LENGTH_SHORT).show());
            } else {
                Favorit f = new Favorit();
                f.setId(endemik.getId());
                f.setNama(endemik.getNama());
                f.setNamaLatin(endemik.getNamaLatin());
                f.setTipe(endemik.getTipe());
                f.setFoto(endemik.getFoto());
                f.setDeskripsi(endemik.getDeskripsi());
                f.setAsal(endemik.getAsal());
                f.setSebaran(endemik.getSebaran());
                f.setStatus(endemik.getStatus());
                f.setFamili(endemik.getFamili());
                f.setGenus(endemik.getGenus());
                f.setVidio(endemik.getVidio());
                db.favoritDao().insert(f);
                isFavorit = true;
                runOnUiThread(() -> Toast.makeText(this, "Ditambahkan ke favorit", Toast.LENGTH_SHORT).show());
            }
            invalidateOptionsMenu();
        }).start();
    }
}