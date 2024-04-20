package com.kevinthegreat.colorfulworld.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ShortArrayKDTreeTest {
    @Test
    void testShortArrayKDTree() {
        short[][] values = new short[][]{{7, 2}, {5, 4}, {9, 6}, {4, 7}, {8, 1}, {2, 3}};
        ShortArrayKDTree tree = new ShortArrayKDTree(2, values);
        Assertions.assertArrayEquals(new short[]{2, 3}, tree.getNearest(new short[]{2, 2}));
        Assertions.assertArrayEquals(new short[]{7, 2}, tree.getNearest(new short[]{5, 1}));
        Assertions.assertArrayEquals(new short[]{5, 4}, tree.getNearest(new short[]{5, 3}));
        Assertions.assertArrayEquals(new short[]{7, 2}, tree.getNearest(new short[]{8, 3}));
        Assertions.assertArrayEquals(new short[]{7, 2}, tree.getNearest(new short[]{8, 3}));
        Assertions.assertArrayEquals(new short[]{9, 6}, tree.getNearest(new short[]{7, 6}));
        short[] nearest = tree.getNearest(new short[]{7, 4});
        Assertions.assertTrue(Arrays.equals(nearest, new short[]{7, 2}) || Arrays.equals(nearest, new short[]{5, 4}));
        nearest = tree.getNearest(new short[]{7, 5});
        Assertions.assertTrue(Arrays.equals(nearest, new short[]{5, 4}) || Arrays.equals(nearest, new short[]{9, 6}));
        nearest = tree.getNearest(new short[]{6, 6});
        Assertions.assertTrue(Arrays.equals(nearest, new short[]{5, 4}) || Arrays.equals(nearest, new short[]{4, 7}));
    }
}
