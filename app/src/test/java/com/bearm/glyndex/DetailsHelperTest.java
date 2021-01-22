package com.bearm.glyndex;

import com.bearm.glyndex.helpers.DetailsHelper;

import org.junit.Test;

import static org.junit.Assert.*;

public class DetailsHelperTest {

    private static String GREEN_ARROW = "ic_up_green_arrow";
    private static String YELLOW_ARROW = "ic_up_yellow_arrow";
    private static String RED_ARROW = "ic_up_red_arrow";
    private static String BLUE_ARROW = "ic_up_blue_arrow";

    @Test
    public void getIGIconName_Red() {
        assertEquals(DetailsHelper.getIGIconName(80), RED_ARROW);
    }

    @Test
    public void getIGIconName_Yellow() {
        assertEquals(DetailsHelper.getIGIconName(60), YELLOW_ARROW);
    }

    @Test
    public void getIGIconName_Green() {
        assertEquals(DetailsHelper.getIGIconName(50), GREEN_ARROW);
    }

    @Test
    public void getIGIconName_Blue() {
        assertEquals(DetailsHelper.getIGIconName(null), BLUE_ARROW);
    }

    @Test
    public void getIGIconName_Green_zero() {
        assertEquals(DetailsHelper.getIGIconName(0), GREEN_ARROW);
    }

    @Test
    public void getIGIconName_Blue_minus() {
        assertEquals(DetailsHelper.getIGIconName(-2), BLUE_ARROW);
    }
}