package com.kevinthegreat.colorfulconcrete.block;

import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColorfulConcretePowderBlock extends ConcretePowderBlock implements ColorfulBlockEntityProvider {
    public ColorfulConcretePowderBlock(Block hardened, Settings settings) {
        super(hardened, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.isClient) {
            world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).ifPresent((blockEntity) -> blockEntity.readFrom(itemStack));
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).map((blockEntity) -> blockEntity.getPickStack(new ItemStack(this))).orElse(super.getPickStack(world, pos, state));
    }
}
