package com.waam.book2play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyStadiums extends Fragment {
    RecyclerView mRecyclerView;
    TextView mStadiumIncome;
    private MiniStadiumAdapter miniStadiumAdapter;
    List<StadiumRegister> stadiums;
    private DatabaseReference cardRef;
    private FirebaseUser user;


    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Stadiums");
        View rootView = inflater.inflate(R.layout.activity_my_stadiums, container, false);
        mStadiumIncome = rootView.findViewById(R.id.stadiumIncome);
        mRecyclerView = rootView.findViewById(R.id.stadiumRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        stadiums = new ArrayList<>();
        cardRef = FirebaseDatabase.getInstance().getReference("stadiums");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();

        TotalIncomeCal cal = new TotalIncomeCal();
        cardRef.orderByChild("sEmail").equalTo(currentUserEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long numberOfBookings = 0;
                stadiums.clear();
                final double[] totalIncome = {0};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    StadiumRegister stadium = postSnapshot.getValue(StadiumRegister.class);
                    stadium.setsKey(postSnapshot.getKey());
                    stadiums.add(stadium);
                    numberOfBookings = postSnapshot.child("bookings").getChildrenCount();
                    totalIncome[0] = cal.calculateNewTotal(totalIncome[0],numberOfBookings, Double.parseDouble(stadium.getsPrice()));
                }
                mStadiumIncome.setText("Rs." + String.valueOf(totalIncome[0]));
                miniStadiumAdapter = new MiniStadiumAdapter(getContext(), stadiums);
                mRecyclerView.setAdapter(miniStadiumAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }
}
