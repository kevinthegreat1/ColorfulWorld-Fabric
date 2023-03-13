package com.kevinthegreat.colorfulworld.item;

import com.kevinthegreat.colorfulworld.util.Util;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ColorfulItem extends BlockItem {
    public ColorfulItem(Block block) {
        this(block, new Settings());
    }

    public ColorfulItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.color", Util.getColorCodeFromStack(stack)).formatted(Formatting.GRAY));
    }
}
