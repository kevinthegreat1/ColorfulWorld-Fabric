package com.kevinthegreat.colorfulconcrete.block;

import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import com.kevinthegreat.colorfulconcrete.block.entity.ColorfulBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
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
            world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readFrom(itemStack));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(ColorfulConcrete.COLORFUL_CONCRETE_POWDER) && newState.isOf(ColorfulConcrete.COLORFUL_CONCRETE)) {
            return;
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
            return;
        }
        int color = (int) world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).map(ColorfulBlockEntity::getRenderAttachmentData).orElse(0);
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        if (fallingBlockEntity.blockEntityData == null) {
            fallingBlockEntity.blockEntityData = new NbtCompound();
        }
        fallingBlockEntity.blockEntityData.putInt(ColorfulConcrete.COLOR_KEY, color);
        configureFallingBlockEntity(fallingBlockEntity);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).map(blockEntity -> blockEntity.getPickStack(new ItemStack(this))).orElse(super.getPickStack(world, pos, state));
    }
}
