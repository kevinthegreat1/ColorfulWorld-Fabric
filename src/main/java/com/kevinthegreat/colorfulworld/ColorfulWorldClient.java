package com.kevinthegreat.colorfulworld;

import com.kevinthegreat.colorfulworld.block.entity.ColorfulBlockEntity;
import com.kevinthegreat.colorfulworld.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class ColorfulWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? (int) view.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).or(() -> view.getBlockEntity(pos.down(), ColorfulWorld.COLORFUL_BLOCK_ENTITY)).map(ColorfulBlockEntity::getRenderAttachmentData).orElse(0) : 0, ColorfulWorld.COLORFUL_CONCRETE, ColorfulWorld.COLORFUL_CONCRETE_POWDER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Util.getColorFromStack(stack), ColorfulWorld.COLORFUL_CONCRETE_ITEM, ColorfulWorld.COLORFUL_CONCRETE_POWDER_ITEM);
    }
}
