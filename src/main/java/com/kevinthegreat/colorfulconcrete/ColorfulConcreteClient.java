package com.kevinthegreat.colorfulconcrete;

import com.kevinthegreat.colorfulconcrete.block.entity.ColorfulBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.BlockItem;

import java.util.Optional;

public class ColorfulConcreteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null ? (int) view.getBlockEntity(pos, ColorfulConcrete.COLORFUL_BLOCK_ENTITY).map((ColorfulBlockEntity::getRenderAttachmentData)).orElse(0) : 0, ColorfulConcrete.COLORFUL_CONCRETE, ColorfulConcrete.COLORFUL_CONCRETE_POWDER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Optional.ofNullable(BlockItem.getBlockEntityNbt(stack)).map((nbtCompound) -> nbtCompound.getInt("Color")).orElse(0), ColorfulConcrete.COLORFUL_CONCRETE_ITEM, ColorfulConcrete.COLORFUL_CONCRETE_POWDER_ITEM);
    }
}
