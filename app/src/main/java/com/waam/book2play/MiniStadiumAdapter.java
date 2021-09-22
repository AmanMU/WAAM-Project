package com.waam.book2play;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MiniStadiumAdapter extends RecyclerView.Adapter<MiniStadiumAdapter.MiniStadiumViewHolder> {

    private Context mContext;
    private List<StadiumRegister> mStadiums;

    public MiniStadiumAdapter(Context context, List<StadiumRegister> stadiums) {
        mStadiums = stadiums;
        mContext = context;
    }

    @NonNull
    @Override
    public MiniStadiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.mini_stadium_card_layout, parent, false);
        return new MiniStadiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniStadiumViewHolder holder, int position) {
        StadiumRegister currentStadium = mStadiums.get(position);
        holder.stadiumName.setText(currentStadium.getsName());
    }

    @Override
    public int getItemCount() {
        return mStadiums.size();
    }

    public class MiniStadiumViewHolder extends RecyclerView.ViewHolder {
        public TextView stadiumName;
        public Button editBtn,deleteBtn;

        public MiniStadiumViewHolder(@NonNull View itemView) {
            super(itemView);

            stadiumName = itemView.findViewById(R.id.myStadiumName);
            editBtn = itemView.findViewById(R.id.stadiumEditBtn);
            deleteBtn = itemView.findViewById(R.id.stadiumDeleteBtn);
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int clickPosition = getAdapterPosition();  // get position of clicked item
//                    StadiumRegister stadium = mStadiums.get(clickPosition);
                    Toast.makeText(context, "EDIT BUTTON OF " + clickPosition + " CLICKED",Toast.LENGTH_SHORT).show();
                }
            });
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}