package com.kevinthegreat.colorfulconcrete.block;

import com.google.common.collect.ImmutableMap;
import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import com.kevinthegreat.colorfulconcrete.block.entity.ColorfulBlockEntity;
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
        return world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).map(ColorfulBlockEntity::getRenderAttachmentData).map(ColorfulConcrete.mapColorGetter::getMapColor).orElse(super.getMapColor(world, pos));
    }
}
