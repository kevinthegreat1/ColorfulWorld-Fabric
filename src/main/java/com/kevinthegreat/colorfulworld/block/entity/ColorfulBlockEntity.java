package com.kevinthegreat.colorfulworld.block.entity;

import com.kevinthegreat.colorfulworld.ColorfulWorld;
import com.kevinthegreat.colorfulworld.util.Util;
import net.fabricmc.fabric.api.blockview.v2.RenderDataBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class ColorfulBlockEntity extends BlockEntity implements RenderDataBlockEntity {
    private int color;

    public ColorfulBlockEntity(BlockPos pos, BlockState state) {
        super(ColorfulWorld.COLORFUL_BLOCK_ENTITY, pos, state);
    }

    public void readFrom(ItemStack stack) {
        color = Util.getColorFromStack(stack);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains(ColorfulWorld.COLOR_KEY)) {
            color = nbt.getInt(ColorfulWorld.COLOR_KEY);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt(ColorfulWorld.COLOR_KEY, color);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public ItemStack getPickStack(ItemStack itemStack) {
        itemStack.applyComponentsFrom(createComponentMap());
        return itemStack;
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        color = components.getOrDefault(ColorfulWorld.COLOR, 0);
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(ColorfulWorld.COLOR, color);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeFromCopiedStackNbt(NbtCompound nbt) {
        super.removeFromCopiedStackNbt(nbt);
        nbt.remove(ColorfulWorld.COLOR_KEY);
    }

    @Override
    public Object getRenderData() {
        return color;
    }
}
