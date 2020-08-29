package com.bearm.glyndex;

public class DetailsHelper {

    private static String GREEN_ARROW = "ic_up_green_arrow";
    private static String YELLOW_ARROW = "ic_up_yellow_arrow";
    private static String RED_ARROW = "ic_up_red_arrow";
    private static String BLUE_ARROW = "ic_up_blue_arrow";


    public static String getIGIconName(Integer currentFoodGI) {
        String icon = YELLOW_ARROW;

        if (currentFoodGI == null) {
            icon = BLUE_ARROW;
        } else if (currentFoodGI <= 55) {
            icon = GREEN_ARROW;
        } else if (currentFoodGI >= 70) {
            icon = RED_ARROW;
        }
        return icon;
    }
}
