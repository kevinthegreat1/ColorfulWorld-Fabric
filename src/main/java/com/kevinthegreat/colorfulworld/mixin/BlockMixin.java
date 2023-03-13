package com.kevinthegreat.colorfulworld.mixin;

import com.kevinthegreat.colorfulworld.block.ColorfulBlockEntityProvider;
import com.kevinthegreat.colorfulworld.block.ColorfulBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Block.class)
public abstract class BlockMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/state/StateManager$Builder;build(Ljava/util/function/Function;Lnet/minecraft/state/StateManager$Factory;)Lnet/minecraft/state/StateManager;"))
    private StateManager.Factory<Block, BlockState> colorfulworld_replaceWithCustomBlockStateFactory(StateManager.Factory<Block, BlockState> factory) {
        return this instanceof ColorfulBlockEntityProvider ? ColorfulBlockState::new : factory;
    }
}
