package com.waam.book2play;

public class TotalIncomeCal {

    public TotalIncomeCal(){}

    public double calculateNewTotal(double currentTotal, long numberOfBookings, double stadiumPrice) {
        return currentTotal + (numberOfBookings * stadiumPrice);
    }
}
