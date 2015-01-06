package com.example.niepewnosci.pomiarowe.entity;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ZestawTest {
    Zestaw zestaw;
    @Before
    public void setUp() throws Exception {
        List<Float> dane = new ArrayList<Float>();
        dane.add(1.5f);
        dane.add(1.6f);
        dane.add(1.9f);
        zestaw = new Zestaw();
        zestaw.setDane(dane);
    }

    @org.junit.Test
    public void testGetDaneAsString() throws Exception {
      assertEquals(new String(), zestaw.getDaneAsString());
    }
}