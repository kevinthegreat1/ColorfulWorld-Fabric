package com.kevinthegreat.colorfulworld.util;

import com.google.common.collect.ImmutableMap;
import com.kevinthegreat.colorfulworld.mixin.MapColorAccessor;
import net.minecraft.block.MapColor;

import java.util.HashMap;
import java.util.Map;

public class MapColorGetter {
    private final ShortArrayKDTree mapColorTree;
    private final Map<short[], MapColor> rgbToMapColor;
    private final Map<Integer, MapColor> mapColorCache = new HashMap<>();

    public MapColorGetter() {
        @SuppressWarnings("UnstableApiUsage")
        ImmutableMap.Builder<short[], MapColor> builder = ImmutableMap.builderWithExpectedSize(MapColorAccessor.getCOLORS().length);
        for (MapColor mapColor : MapColorAccessor.getCOLORS()) {
            if (mapColor != null && mapColor != MapColor.CLEAR) {
                builder.put(getRGB(mapColor.color), mapColor);
            }
        }
        rgbToMapColor = builder.build();
        mapColorTree = new ShortArrayKDTree(3, rgbToMapColor.keySet().toArray(new short[rgbToMapColor.size()][]));
    }

    public MapColor getMapColor(Object color) {
        return getMapColor((int) color);
    }

    public MapColor getMapColor(int color) {
        return mapColorCache.computeIfAbsent(color, this::calculateNearestMapColor);
    }

    private MapColor calculateNearestMapColor(int color) {
        return rgbToMapColor.get(mapColorTree.getNearest(getRGB(color)));
    }

    private static short[] getRGB(int color) {
        short r = (short) ((color & 0xFF0000) >> 16);
        short g = (short) ((color & 0xFF00) >> 8);
        short b = (short) ((color & 0xFF));
        return new short[]{r, g, b};
    }
}
