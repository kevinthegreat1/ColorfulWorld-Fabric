package com.kevinthegreat.colorfulworld.util;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import net.minecraft.item.ItemStack;

import java.util.HexFormat;

public class Util {
    private static final HexFormat HEX_FORMAT = HexFormat.of().withUpperCase();

    public static int getColorFromStack(ItemStack stack) {
        return stack.getOrDefault(ColorfulWorld.COLOR, 0);
    }

    public static String getColorCodeFromStack(ItemStack stack) {
        return "#" + HEX_FORMAT.toHexDigits(getColorFromStack(stack)).substring(2);
    }
}
