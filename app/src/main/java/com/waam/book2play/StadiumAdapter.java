package com.waam.book2play;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Image> mStadiums;

    public StadiumAdapter(Context context, List<Image> stadiums) {
        mStadiums = stadiums;
        mContext = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stadium_card_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image currentImageCard = mStadiums.get(position);
        holder.stadiumName.setText(currentImageCard.getName());
        holder.stadiumLocation.setText(currentImageCard.getLocation());
        holder.stadiumClosingTime.setText(currentImageCard.getClosingTime());
        holder.stadiumPrice.setText(currentImageCard.getPrice());

        Picasso.get().load(currentImageCard.getImage())
                .fit()
                .centerCrop()
                .into(holder.stadiumImage);

    }

    @Override
    public int getItemCount() {
        return mStadiums.size();
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
            stadiumImage = itemView.findViewById(R.id.sImage);
        }
    }
}
