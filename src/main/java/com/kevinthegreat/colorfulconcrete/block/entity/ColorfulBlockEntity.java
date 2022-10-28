package com.kevinthegreat.colorfulconcrete.block.entity;

import com.kevinthegreat.colorfulconcrete.ColorfulConcrete;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class ColorfulBlockEntity extends BlockEntity implements RenderAttachmentBlockEntity {
    private int color;

    public ColorfulBlockEntity(BlockPos pos, BlockState state) {
        super(ColorfulConcrete.COLORFUL_BLOCK_ENTITY, pos, state);
    }

    public void readFrom(ItemStack itemStack) {
        color = Optional.ofNullable(BlockItem.getBlockEntityNbt(itemStack)).map((nbtCompound) -> nbtCompound.getInt(ColorfulConcrete.COLOR_KEY)).orElse(0);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        color = nbt.getInt(ColorfulConcrete.COLOR_KEY);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(ColorfulConcrete.COLOR_KEY, color);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public ItemStack getPickStack(ItemStack itemStack) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putInt(ColorfulConcrete.COLOR_KEY, color);
        BlockItem.setBlockEntityNbt(itemStack, ColorfulConcrete.COLORFUL_BLOCK_ENTITY, nbtCompound);
        return itemStack;
    }

    @Override
    public Object getRenderAttachmentData() {
        return color;
    }
}
