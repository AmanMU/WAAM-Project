package com.waam.book2play;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView stadiumName,stadiumLocation,stadiumClosingTime,stadiumPrice;
        public ImageView stadiumImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            stadiumName = itemView.findViewById(R.id.sName);
            stadiumLocation = itemView.findViewById(R.id.sLocation);
            stadiumClosingTime = itemView.findViewById(R.id.sClosingTime);
            stadiumPrice = itemView.findViewById(R.id.sPrice);
            stadiumName = itemView.findViewById(R.id.sImage);
        }
    }
}
