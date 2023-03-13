package com.kevinthegreat.colorfulworld;

import com.kevinthegreat.colorfulworld.block.entity.ColorfulBlockEntity;
import com.kevinthegreat.colorfulworld.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class ColorfulWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ColorfulWorld.COLORFUL_GLASS, RenderLayer.getTranslucent());
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? (int) view.getBlockEntity(pos, ColorfulWorld.COLORFUL_BLOCK_ENTITY).or(() -> view.getBlockEntity(pos.down(), ColorfulWorld.COLORFUL_BLOCK_ENTITY)).map(ColorfulBlockEntity::getRenderAttachmentData).orElse(0) : 0, ColorfulWorld.COLORFUL_CONCRETE, ColorfulWorld.COLORFUL_CONCRETE_POWDER, ColorfulWorld.COLORFUL_GLASS, ColorfulWorld.COLORFUL_GLASS_PANE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Util.getColorFromStack(stack), ColorfulWorld.COLORFUL_CONCRETE_ITEM, ColorfulWorld.COLORFUL_CONCRETE_POWDER_ITEM, ColorfulWorld.COLORFUL_GLASS_ITEM, ColorfulWorld.COLORFUL_GLASS_PANE_ITEM);
    }
}
