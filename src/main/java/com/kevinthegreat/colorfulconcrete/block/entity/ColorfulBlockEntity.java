package com.kevinthegreat.colorfulconcrete.block.entity;

import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ColorfulBlockEntity extends BlockEntity {
    public ColorfulBlockEntity(BlockPos pos, BlockState state) {
        super(ColorfulConcrete.COLORFUL_BLOCK_ENTITY, pos, state);
    }
}
