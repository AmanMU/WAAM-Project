package com.waam.book2play;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.MyBookingViewHolder> {

    private Context mContext;
    private List<Booking> mBooking;

    public BookingsAdapter(Context context, List<Booking> bookings) {
        mBooking = bookings;
        mContext = context;

    }

    @NonNull
    @Override
    public MyBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_booking_card, parent, false);
        return new MyBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookingViewHolder holder, int position) {
        Booking myBooking = mBooking.get(position);
        holder.stadiumName.setText(myBooking.getStadiumName());
        holder.mDate.setText(myBooking.getDate());
        holder.mTime.setText(myBooking.getTime());
    }

    @Override
    public int getItemCount() {
        return mBooking.size();
    }

    public class MyBookingViewHolder extends RecyclerView.ViewHolder {
        public TextView stadiumName;
        public TextView mDate;
        public TextView mTime;

        public MyBookingViewHolder(@NonNull View itemView) {
            super(itemView);

            stadiumName = itemView.findViewById(R.id.stadiumName);
            mDate = itemView.findViewById(R.id.bookedDate);
            mTime = itemView.findViewById(R.id.bookedTime);
        }
    }
}
