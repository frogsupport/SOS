package com.sos.game.sos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static com.sos.game.sos.SOSController.*;

public class SOSAppTest {
    @Test
    public void testSelectionSort() {

        int[] testArr = new int[]{10, 15, 6, 7};
        int[] sortedArr = selectionSort(testArr);
        int[] expectedArr = new int[]{6, 7, 10, 15};

        assertArrayEquals(expectedArr, sortedArr);
    }

    @Test
    public void testGetHelloWorld() {

        assertSame("Hello World!", getHelloWorld());
    }
}
