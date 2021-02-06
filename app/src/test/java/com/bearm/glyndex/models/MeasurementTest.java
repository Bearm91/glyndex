package com.bearm.glyndex.models;

import android.icu.util.Measure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementTest {

    Measurement myMeasurement;
    String name;
    float chRationPerMeasurement;
    int id;
    int foodId;

    @Before
    public void setUp() throws Exception {
        myMeasurement = new Measurement();
        name = "Un puñado con la mano cerrada";
        foodId = 2;
        id = 1;
        chRationPerMeasurement = 2.3F;
    }

    @Test
    public void getterSetterIdTest() {
        myMeasurement.setId(id);
        Assert.assertEquals(myMeasurement.getId(), id);
    }

    @Test
    public void getterSetterNameTest() {
        myMeasurement.setName(name);
        Assert.assertEquals(myMeasurement.getName(), name);
    }

    @Test
    public void getterSetterChRationPerMeasurementTest() {
        myMeasurement.setChRationPerMeasurement(chRationPerMeasurement);
        Assert.assertTrue(myMeasurement.getChRationPerMeasurement() == chRationPerMeasurement);
    }

    @Test
    public void getterSetterFoodIdTest() {
        myMeasurement.setFoodId(foodId);
        Assert.assertEquals(myMeasurement.getFoodId(), foodId);
    }
}