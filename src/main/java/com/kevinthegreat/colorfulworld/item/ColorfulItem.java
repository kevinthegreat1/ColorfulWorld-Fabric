package com.kevinthegreat.colorfulworld.item;

import com.kevinthegreat.colorfulworld.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ColorfulItem extends BlockItem {
    public ColorfulItem(Block block) {
        this(block, new Settings());
    }

    public ColorfulItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        tooltip.add(Text.translatable("item.color", Util.getColorCodeFromStack(stack)).formatted(Formatting.GRAY));
    }
}
