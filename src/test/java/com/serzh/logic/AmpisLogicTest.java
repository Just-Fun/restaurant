package com.serzh.logic;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AmpisLogicTest {

    private int[] array1 = {1, 1, 2, 2, 3};
    private int[] array2 = {-1, 2, 4, 2, -1};

    private AmpisLogic ampisLogic = new AmpisLogic();

    @Test
    public void getSingleTest() {
        assertEquals(3, ampisLogic.getSingle(array1));
        assertEquals(4, ampisLogic.getSingle(array2));
    }

    @Test
    public void getSingle2Test() {
        assertEquals(3, ampisLogic.getSingle2(array1));
        assertEquals(4, ampisLogic.getSingle2(array2));
    }

    @Test
    public void productExceptSelfTest() {
        assertArrayEquals(new int[]{24, 12, 8, 6}, ampisLogic.productExceptSelf(new int[]{1, 2, 3, 4}));
    }
}
