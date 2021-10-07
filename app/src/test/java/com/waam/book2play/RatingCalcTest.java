package com.waam.book2play;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class RatingCalcTest {


    private RatingCalc ratingcalc;


    @Before
    public void setup(){
        ratingcalc=new RatingCalc();

    }


    @Test
    public void testrating(){
        float result=ratingcalc.sumrating(10.0f,10.0f);
        assertEquals(20.0f,result);

    }
}
