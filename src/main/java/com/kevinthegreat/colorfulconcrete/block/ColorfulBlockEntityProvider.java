package com.kevinthegreat.colorfulconcrete.block;

import com.kevinthegreat.colorfulconcrete.block.entity.ColorfulBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;


public interface ColorfulBlockEntityProvider extends BlockEntityProvider {
    @Override
    default BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ColorfulBlockEntity(pos, state);
    }
}
