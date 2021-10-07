package com.waam.book2play;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NoOfSessionCalTest {
    private NoOfSessionsCal noOfSessionsCal;

    @Before
    public void setup(){
        noOfSessionsCal = new NoOfSessionsCal();
    }

    @Test
    public void timeConvertTest() throws Exception {
        String result = noOfSessionsCal.timeConvert("12:00 AM");
        Assert.assertEquals("24", result);
    }

    @Test
    public void timeDifferenceTest() throws Exception {
        String result = noOfSessionsCal.calcDif("09:00 AM", "10:00 PM");
        Assert.assertEquals("13 sessions", result);
    }
}
