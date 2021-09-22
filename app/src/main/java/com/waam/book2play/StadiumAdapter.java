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

public class StadiumAdapter extends RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder> {

    private Context mContext;
    private List<StadiumRegister> mStadiums;

    public StadiumAdapter(Context context, List<StadiumRegister> stadiums) {
        mStadiums = stadiums;
        mContext = context;
    }

    @NonNull
    @Override
    public StadiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stadium_card_layout, parent, false);
        return new StadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StadiumViewHolder holder, int position) {
        StadiumRegister currentImageCard = mStadiums.get(position);
        holder.stadiumName.setText(currentImageCard.getsName());
        holder.stadiumLocation.setText(currentImageCard.getsLocation());
        holder.stadiumClosingTime.setText(currentImageCard.getsCT());
        holder.stadiumPrice.setText(currentImageCard.getsPrice());

        Picasso.get().load(currentImageCard.getsImageURL())
                .fit()
                .centerCrop()
                .into(holder.stadiumImage);

    }

    @Override
    public int getItemCount() {
        return mStadiums.size();
    }

    public class StadiumViewHolder extends RecyclerView.ViewHolder {
        public TextView stadiumName,stadiumLocation,stadiumClosingTime,stadiumPrice;
        public ImageView stadiumImage;

        public StadiumViewHolder(@NonNull View itemView) {
            super(itemView);

            stadiumName = itemView.findViewById(R.id.sName);
            stadiumLocation = itemView.findViewById(R.id.sLocation);
            stadiumClosingTime = itemView.findViewById(R.id.sClosingTime);
            stadiumPrice = itemView.findViewById(R.id.sPrice);
            stadiumImage = itemView.findViewById(R.id.sImage);
        }
    }
}
