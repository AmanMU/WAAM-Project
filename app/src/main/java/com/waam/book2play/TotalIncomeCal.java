package com.waam.book2play;

public class TotalIncomeCal {

    public TotalIncomeCal(){}

    public long calculateNewTotal(long currentTotal, long numberOfBookings, long stadiumPrice) {
        return currentTotal + (numberOfBookings * stadiumPrice);
    }
}
