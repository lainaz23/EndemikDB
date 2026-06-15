package com.laila.endemikdb;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.laila.endemikdb.database.EndemikDatabase;
import com.laila.endemikdb.model.Endemik;
import com.laila.endemikdb.network.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvStatus = findViewById(R.id.tv_status);

        new Thread(() -> {
            EndemikDatabase db = EndemikDatabase.getInstance(this);
            int count = db.endemikDao().count();

            if (count > 0) {
                try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                goToHome();
            } else {
                runOnUiThread(() -> tvStatus.setText("Mengambil data dari server..."));
                fetchData(db);
            }
        }).start();
    }

    private void fetchData(EndemikDatabase db) {
        ApiClient.getApiService().getEndemik().enqueue(new Callback<List<Endemik>>() {
            @Override
            public void onResponse(Call<List<Endemik>> call, Response<List<Endemik>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new Thread(() -> {
                        db.endemikDao().insertAll(response.body());
                        runOnUiThread(() -> tvStatus.setText("Data tersimpan, lanjut"));
                        goToHome();
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<List<Endemik>> call, Throwable t) {
                runOnUiThread(() -> tvStatus.setText("Gagal mengambil data: " + t.getMessage()));
            }
        });
    }

    private void goToHome() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}