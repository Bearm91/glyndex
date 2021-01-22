package com.bearm.glyndex.helpers;

public class DetailsHelper {

    public static String getIGIconName(Integer currentFoodGI) {
        String icon = Constants.YELLOW_ARROW;

        if (currentFoodGI == null) {
            icon = Constants.BLUE_ARROW;
        } else if (currentFoodGI <= 55) {
            icon = Constants.GREEN_ARROW;
        } else if (currentFoodGI >= 70) {
            icon = Constants.RED_ARROW;
        }
        return icon;
    }
}
