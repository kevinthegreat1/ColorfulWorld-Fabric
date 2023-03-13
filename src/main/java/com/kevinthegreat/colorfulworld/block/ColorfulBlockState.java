package com.kevinthegreat.colorfulworld.block;

import com.google.common.collect.ImmutableMap;
import com.kevinthegreat.colorfulworld.ColorfulWorld;
import com.kevinthegreat.colorfulworld.block.entity.ColorfulBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ColorfulBlockState extends BlockState {
    public ColorfulBlockState(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
        super(block, immutableMap, mapCodec);
    }

    @Override
    public MapColor getMapColor(BlockView world, BlockPos pos) {
        return world.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).map(ColorfulBlockEntity::getRenderAttachmentData).map(ColorfulWorld.mapColorGetter::getMapColor).orElse(super.getMapColor(world, pos));
    }
}
