package com.laila.endemikdb.network;

import com.laila.endemikdb.model.Endemik;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endemik.json")
    Call<List<Endemik>> getEndemik();
}