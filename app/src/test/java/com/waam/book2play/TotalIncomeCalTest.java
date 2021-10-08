package com.waam.book2play;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TotalIncomeCalTest {
    private TotalIncomeCal totalIncomeCal;

    @Before
    public void setup(){
        totalIncomeCal = new TotalIncomeCal();
    }

    @Test
    public void calculateNewTotalTest() {
        double result = totalIncomeCal.calculateNewTotal(4000,5,4000);
        Assert.assertEquals(24000, result, 0);
    }
}
