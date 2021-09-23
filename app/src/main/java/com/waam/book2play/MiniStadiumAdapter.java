package com.waam.book2play;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MiniStadiumAdapter extends RecyclerView.Adapter<MiniStadiumAdapter.MiniStadiumViewHolder> {

    private Context mContext;
    private List<StadiumRegister> mStadiums;
    private FirebaseStorage mStorage;

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
        public Button editBtn, deleteBtn;

        public MiniStadiumViewHolder(@NonNull View itemView) {
            super(itemView);

            stadiumName = itemView.findViewById(R.id.myStadiumName);
            editBtn = itemView.findViewById(R.id.stadiumEditBtn);
            deleteBtn = itemView.findViewById(R.id.stadiumDeleteBtn);
            mStorage = FirebaseStorage.getInstance();
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, EditStadium.class);

                    int clickPosition = getAdapterPosition();

                    StadiumRegister stadium = mStadiums.get(clickPosition);
                    intent.putExtra("stadium", stadium);
                    context.startActivity(intent);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int posi = getAdapterPosition();

                    StadiumRegister stadiumD = mStadiums.get(posi);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are you sure?");
                    builder.setMessage("Deleted data cannot be retrieved");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            StorageReference sImage = mStorage.getReferenceFromUrl(stadiumD.getsImageURL());
                            sImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    FirebaseDatabase.getInstance().getReference().child("stadiums")
                                            .child(stadiumD.getsKey()).removeValue();
                                    Toast.makeText(context, "Stadium successfully deleted.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Stadium delete failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Delete cancelled.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }
    }
}