package com.waam.book2play;

import android.os.Bundle;
import android.util.Log;
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

public class MyBookings extends Fragment {
    RecyclerView bookingRecyclerView;
    private BookingsAdapter singleBookings;
    List<Booking> bookings;
    private DatabaseReference cardRefer;
    private FirebaseUser user;
    TextView totalBookingTv;


    @Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Bookings");
        View rootView = inflater.inflate(R.layout.activity_my_bookings, container, false);

        totalBookingTv = rootView.findViewById(R.id.totalAmount);
        bookingRecyclerView = rootView.findViewById(R.id.bookingRecyclerView);
        bookingRecyclerView.setHasFixedSize(true);
        bookingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookings = new ArrayList<>();
        cardRefer = FirebaseDatabase.getInstance().getReference("stadiums");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = user.getEmail();
        final double[] totalPrice = {0};

        cardRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String stadiumPrice = postSnapshot.getValue(StadiumRegister.class).getsPrice();
                    double currentStadiumPrice = Double.parseDouble(stadiumPrice);
                    TotalBookingCal total = new TotalBookingCal();
                    postSnapshot.child("bookings").getRef().orderByChild("email").equalTo(currentUserEmail).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot testSnapshot) {
                            Long bookingCount = testSnapshot.getChildrenCount();
                            for (DataSnapshot bookingSnapshot : testSnapshot.getChildren()) {
                                Booking myBooking = (Booking) bookingSnapshot.getValue(Booking.class);
                                bookings.add(myBooking);
                            }

                            totalPrice[0] = total.calculateBookingTotal(totalPrice[0], currentStadiumPrice, bookingCount);

                            singleBookings = new BookingsAdapter(getContext(), bookings);
                            bookingRecyclerView.setAdapter(singleBookings);

                            totalBookingTv.setText(String.valueOf(totalPrice[0]));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
}

