package com.waam.book2play;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TotalBookingCalTest {
    private TotalBookingCal totalBookingCal;

    @Before
    public void setup(){
        totalBookingCal = new TotalBookingCal();
    }

    @Test
    public void calculateBookingTotalTest() {
        double result = totalBookingCal.calculateBookingTotal(4000.0,2500.0,4);
        Assert.assertEquals(14000.0, result, 0);
    }
}
