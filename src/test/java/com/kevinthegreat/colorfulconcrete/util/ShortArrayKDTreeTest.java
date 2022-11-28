package com.kevinthegreat.colorfulconcrete.util;

import java.util.Arrays;

public class ShortArrayKDTreeTest {
    public static void main(String[] args) {
        short[][] values = new short[][]{{7, 2}, {5, 4}, {9, 6}, {4, 7}, {8, 1}, {2, 3}};
        ShortArrayKDTree tree = new ShortArrayKDTree(2, values);
        assert Arrays.equals(tree.getNearest(new short[]{2, 2}), new short[]{2, 3});
        assert Arrays.equals(tree.getNearest(new short[]{5, 1}), new short[]{7, 2});
        assert Arrays.equals(tree.getNearest(new short[]{5, 3}), new short[]{5, 4});
        assert Arrays.equals(tree.getNearest(new short[]{8, 3}), new short[]{7, 2});
        assert Arrays.equals(tree.getNearest(new short[]{8, 3}), new short[]{7, 2});
        assert Arrays.equals(tree.getNearest(new short[]{7, 6}), new short[]{9, 6});
        short[] nearest = tree.getNearest(new short[]{7, 4});
        assert Arrays.equals(nearest, new short[]{7, 2}) || Arrays.equals(nearest, new short[]{5, 4});
        nearest = tree.getNearest(new short[]{7, 5});
        assert Arrays.equals(nearest, new short[]{5, 4}) || Arrays.equals(nearest, new short[]{9, 6});
        nearest = tree.getNearest(new short[]{6, 6});
        assert Arrays.equals(nearest, new short[]{5, 4}) || Arrays.equals(nearest, new short[]{4, 7});
    }
}
