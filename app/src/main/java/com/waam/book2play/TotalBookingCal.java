package com.waam.book2play;

public class TotalBookingCal {

    public TotalBookingCal(){}

    public double calculateBookingTotal(double totalPrice, double currentStadiumPrice, long bookingCount){
        return totalPrice + (currentStadiumPrice*bookingCount);
    }

}
