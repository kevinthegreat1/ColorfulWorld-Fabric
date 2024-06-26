package com.kevinthegreat.colorfulworld.block;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ColorfulGlassPaneBlock extends PaneBlock implements ColorfulBlockEntityProvider {
    public ColorfulGlassPaneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (world.isClient) {
            world.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readFrom(itemStack));
        }
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).map(blockEntity -> blockEntity.getPickStack(new ItemStack(this))).orElse(super.getPickStack(world, pos, state));
    }
}
