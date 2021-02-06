package com.bearm.glyndex.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category myCategory;
    int id;
    String name;
    String iconName;

    @Before
    public void setUp() throws Exception {
        myCategory = new Category();
        id = 1;
        name = "Legumbres";
        iconName = "legumbres_icon";
    }

    @Test
    public void getterSetterIdTest() {
        myCategory.setId(id);
        Assert.assertEquals(myCategory.getId(), id);
    }

    @Test
    public void getterSetterNameTest() {
        myCategory.setName(name);
        Assert.assertEquals(myCategory.getName(), name);
    }

    @Test
    public void getterSetterIconNameTest() {
        myCategory.setIconName(iconName);
        Assert.assertEquals(myCategory.getIconName(), iconName);
    }

}