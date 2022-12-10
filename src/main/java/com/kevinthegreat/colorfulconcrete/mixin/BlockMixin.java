package com.kevinthegreat.colorfulconcrete.mixin;

import com.kevinthegreat.colorfulconcrete.block.ColorfulBlockEntityProvider;
import com.kevinthegreat.colorfulconcrete.block.ColorfulBlockState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/state/StateManager$Builder;build(Ljava/util/function/Function;Lnet/minecraft/state/StateManager$Factory;)Lnet/minecraft/state/StateManager;"))
    private StateManager<Block, BlockState> colorfulconcrete_build(StateManager.Builder<Block, BlockState> builder, Function<Block, BlockState> defaultStateGetter, StateManager.Factory<Block, BlockState> factory) {
        return builder.build(defaultStateGetter, this instanceof ColorfulBlockEntityProvider ? ColorfulBlockState::new : factory);
    }
}
