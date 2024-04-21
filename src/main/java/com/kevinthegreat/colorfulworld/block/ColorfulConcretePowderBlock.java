package com.kevinthegreat.colorfulworld.block;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
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
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ColorfulConcretePowderBlock extends ConcretePowderBlock implements ColorfulBlockEntityProvider {
    public ColorfulConcretePowderBlock(Block hardened, Settings settings) {
        super(hardened, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.isClient) {
            world.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readFrom(itemStack));
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(ColorfulWorld.COLORFUL_CONCRETE_POWDER) && newState.isOf(ColorfulWorld.COLORFUL_CONCRETE)) {
            return;
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < world.getBottomY()) {
            return;
        }
        @SuppressWarnings("DataFlowIssue")
        int color = (int) world.getBlockEntityRenderData(pos);
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        if (fallingBlockEntity.blockEntityData == null) {
            fallingBlockEntity.blockEntityData = new NbtCompound();
        }
        fallingBlockEntity.blockEntityData.putInt(ColorfulWorld.COLOR_KEY, color);
        configureFallingBlockEntity(fallingBlockEntity);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).map(blockEntity -> blockEntity.getPickStack(new ItemStack(this))).orElse(super.getPickStack(world, pos, state));
    }
}
