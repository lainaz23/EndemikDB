package com.laila.endemikdb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.laila.endemikdb.R;
import com.laila.endemikdb.model.Favorit;
import java.util.List;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Favorit item);
    }

    private List<Favorit> list;
    private OnItemClickListener listener;

    public FavoritAdapter(List<Favorit> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorit item = list.get(position);
        holder.tvNama.setText(item.getNama());
        Glide.with(holder.itemView.getContext())
                .load(item.getFoto())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivFoto);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            tvNama = itemView.findViewById(R.id.tv_nama);
        }
    }
}