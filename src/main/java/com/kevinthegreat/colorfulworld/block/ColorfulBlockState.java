package com.kevinthegreat.colorfulworld.block;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ColorfulBlockState extends BlockState {
    public ColorfulBlockState(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> propertyMap, MapCodec<BlockState> mapCodec) {
        super(block, propertyMap, mapCodec);
    }

    @Override
    public MapColor getMapColor(BlockView world, BlockPos pos) {
        Object color = world.getBlockEntityRenderData(pos);
        return color != null ? ColorfulWorld.MAP_COLOR_GETTER.getMapColor(color) : super.getMapColor(world, pos);
    }
}
