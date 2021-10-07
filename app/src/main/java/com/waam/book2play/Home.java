package com.waam.book2play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayout futsalCard,cricketCard,badmintonCard;
    private StadiumAdapter stadiumAdapter;
    private DatabaseReference cardRef;
    private List<StadiumRegister> stadiums;
    private ShimmerFrameLayout mShimmerViewContainer;
    private final String CRICKET = "Cricket";
    private final String FUTSAL = "Futsal";
    private final String BADMINTON = "Badminton";

    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        View rootView =  inflater.inflate(R.layout.home_fragment, container, false);
        mRecyclerView = rootView.findViewById(R.id.stadiumCardRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mShimmerViewContainer = rootView.findViewById(R.id.shimmerFrameLayout);
        futsalCard = rootView.findViewById(R.id.FutsalCard);
        cricketCard = rootView.findViewById(R.id.CricketCard);
        badmintonCard = rootView.findViewById(R.id.BadmintonCard);


        stadiums = new ArrayList<>();
        cardRef = FirebaseDatabase.getInstance().getReference("stadiums");

        cardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stadiums.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    StadiumRegister stadium = postSnapshot.getValue(StadiumRegister.class);
                    stadium.setsKey(postSnapshot.getKey());
                    stadiums.add(stadium);
                }

                stadiumAdapter = new StadiumAdapter(getContext(), stadiums);
                mRecyclerView.setAdapter(stadiumAdapter);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        cricketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRef.orderByChild("sType").equalTo(CRICKET).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        stadiums.clear();
                        for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                            StadiumRegister stadium = postSnapshot.getValue(StadiumRegister.class);
                            stadium.setsKey(postSnapshot.getKey());
                            stadiums.add(stadium);
                        }

                        stadiumAdapter = new StadiumAdapter(getContext(), stadiums);
                        mRecyclerView.setAdapter(stadiumAdapter);
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        futsalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRef.orderByChild("sType").equalTo(FUTSAL).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        stadiums.clear();
                        for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                            StadiumRegister stadium = postSnapshot.getValue(StadiumRegister.class);
                            stadium.setsKey(postSnapshot.getKey());
                            stadiums.add(stadium);
                        }

                        stadiumAdapter = new StadiumAdapter(getContext(), stadiums);
                        mRecyclerView.setAdapter(stadiumAdapter);
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        badmintonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRef.orderByChild("sType").equalTo(BADMINTON).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        stadiums.clear();
                        for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                            StadiumRegister stadium = postSnapshot.getValue(StadiumRegister.class);
                            stadium.setsKey(postSnapshot.getKey());
                            stadiums.add(stadium);
                        }

                        stadiumAdapter = new StadiumAdapter(getContext(), stadiums);
                        mRecyclerView.setAdapter(stadiumAdapter);
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
    }
}
