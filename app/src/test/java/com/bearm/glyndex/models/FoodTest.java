package com.bearm.glyndex.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FoodTest {

    Food myFood;
    int id;
    String name;
    int gI;
    long gramsPerChRation;

    @Before
    public void setUp() throws Exception {
        myFood = new Food();
        id = 1;
        name = "Manzana";
        gI = 60;
        gramsPerChRation = 24L;
    }

    @Test
    public void getterSetterIdTest() {
        myFood.setId(id);
        Assert.assertEquals(myFood.getId(), id);
    }

    @Test
    public void getterSetterNameTest() {
        myFood.setName(name);
        Assert.assertEquals(myFood.getName(), name);
    }

    @Test
    public void getterSetterGITest() {
        myFood.setGI(gI);
        Assert.assertTrue(myFood.getGI() == gI);
    }

    @Test
    public void getterSetterGramsPerChRationTest() {
        myFood.setGramsPerChRation(gramsPerChRation);
        Assert.assertEquals(myFood.getGramsPerChRation(), gramsPerChRation);
    }
}