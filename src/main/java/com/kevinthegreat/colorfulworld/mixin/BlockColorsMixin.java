package com.kevinthegreat.colorfulworld.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockColors.class)
public abstract class BlockColorsMixin {
    @Redirect(method = "getParticleColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/block/BlockColorProvider;getColor(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;I)I"))
    private int colorfulworld_getColor(BlockColorProvider blockColorProvider, BlockState stateCopy, BlockRenderView nullView, BlockPos nullPos, int tintIndex, BlockState state, World world, BlockPos pos) {
        return blockColorProvider.getColor(state, world, pos, tintIndex);
    }
}
