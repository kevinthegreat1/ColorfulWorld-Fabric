package com.kevinthegreat.colorfulconcrete.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class ShortArrayKDTree {
    private final int dimensions;
    private final @NotNull Node root;

    public ShortArrayKDTree(int dimensions, short[][] values) {
        if (dimensions < 1) {
            throw new IllegalArgumentException("Dimensions must be greater than 0");
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Values must not be empty");
        }
        this.dimensions = dimensions;
        this.root = Objects.requireNonNull(buildTree(values, 0, values.length, 0));
    }

    private Node buildTree(short[][] values, int fromIndex, int toIndex, int depth) {
        if (fromIndex == toIndex) {
            return null;
        }
        Arrays.sort(values, fromIndex, toIndex, Comparator.comparingInt(value -> value[depth]));
        int medianIndex = (fromIndex + toIndex) >> 1;
        return new Node(values[medianIndex], depth, buildTree(values, fromIndex, medianIndex, (depth + 1) % dimensions), buildTree(values, medianIndex + 1, toIndex, (depth + 1) % dimensions));
    }

    public short[] getNearest(short[] target) {
        return getNearest(root, target).value;
    }

    private Node getNearest(@NotNull Node node, short[] target) {
        Node nearest = node.distanceSquared(target);
        if (target[node.splitIndex] < node.value[node.splitIndex]) {
            if (node.left != null) {
                nearest = nearest.min(getNearest(node.left, target));
            }
            if (node.right != null && node.value[node.splitIndex] - target[node.splitIndex] < nearest.distanceSquared) {
                nearest = nearest.min(getNearest(node.right, target));
            }
        } else {
            if (node.right != null) {
                nearest = nearest.min(getNearest(node.right, target));
            }
            if (node.left != null && target[node.splitIndex] - node.value[node.splitIndex] < nearest.distanceSquared) {
                nearest = nearest.min(getNearest(node.left, target));
            }
        }
        return nearest;
    }

    private static class Node {
        short[] value;
        int splitIndex;
        @Nullable Node left;
        @Nullable Node right;
        private int distanceSquared;

        public Node(short[] value, int splitIndex, @Nullable Node left, @Nullable Node right) {
            this.value = value;
            this.splitIndex = splitIndex;
            this.left = left;
            this.right = right;
        }

        private Node distanceSquared(short[] target) {
            int distanceSquared = 0;
            for (int i = 0; i < value.length; i++) {
                int difference = value[i] - target[i];
                distanceSquared += difference * difference;
            }
            this.distanceSquared = distanceSquared;
            return this;
        }

        private Node min(Node node) {
            return node.distanceSquared < this.distanceSquared ? node : this;
        }
    }
}
