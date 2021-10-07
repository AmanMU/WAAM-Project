package com.waam.book2play;





import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RatingCalcTest {


    private RatingCalc ratingcalc;


    @Before
    public void setup(){
        ratingcalc=new RatingCalc();

    }


    @Test
    public void testrating(){
        float result=ratingcalc.sumrating(10.0f,10.0f);
        Assert.assertEquals(20.0f,result, 0 );

    }
}
