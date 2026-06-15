package com.laila.endemikdb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.laila.endemikdb.DetailActivity;
import com.laila.endemikdb.R;
import com.laila.endemikdb.adapter.EndemikAdapter;
import com.laila.endemikdb.database.EndemikDatabase;
import com.laila.endemikdb.model.Endemik;
import java.util.ArrayList;
import java.util.List;

public class TumbuhanFragment extends Fragment {

    RecyclerView rv;
    Spinner spinner;
    List<String> regionList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tumbuhan, container, false);

        rv = view.findViewById(R.id.rv_tumbuhan);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        spinner = view.findViewById(R.id.spinner_region);

        new Thread(() -> {
            List<String> regions = EndemikDatabase.getInstance(getContext()).endemikDao().getAllRegion();
            regionList.clear();
            regionList.add("Semua Region");
            regionList.addAll(regions);

            requireActivity().runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_item, regionList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selected = regionList.get(position);
                        loadData(selected.equals("Semua Region") ? null : selected);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                loadData(null);
            });
        }).start();

        return view;
    }

    private void loadData(String region) {
        new Thread(() -> {
            List<Endemik> list;
            if (region == null) {
                list = EndemikDatabase.getInstance(getContext()).endemikDao().getByTipe("Tumbuhan");
            } else {
                list = EndemikDatabase.getInstance(getContext()).endemikDao().getByTipeAndRegion("Tumbuhan", region);
            }
            requireActivity().runOnUiThread(() -> {
                EndemikAdapter adapter = new EndemikAdapter(list, item -> {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("id", item.getId());
                    startActivity(intent);
                });
                rv.setAdapter(adapter);
            });
        }).start();
    }
}