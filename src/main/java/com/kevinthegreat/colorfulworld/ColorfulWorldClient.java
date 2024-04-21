package com.kevinthegreat.colorfulworld;

import com.kevinthegreat.colorfulworld.util.Util;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.client.render.RenderLayer;

import java.util.Objects;

public class ColorfulWorldClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ColorfulWorld.COLORFUL_GLASS, ColorfulWorld.COLORFUL_GLASS_PANE);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> view != null && pos != null ? (int) Objects.requireNonNullElseGet(view.getBlockEntityRenderData(pos), () -> Objects.requireNonNullElse(view.getBlockEntityRenderData(pos.down()), 0)) : 0, ColorfulWorld.COLORFUL_CONCRETE, ColorfulWorld.COLORFUL_CONCRETE_POWDER, ColorfulWorld.COLORFUL_GLASS, ColorfulWorld.COLORFUL_GLASS_PANE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> Util.getColorFromStack(stack), ColorfulWorld.COLORFUL_CONCRETE_ITEM, ColorfulWorld.COLORFUL_CONCRETE_POWDER_ITEM, ColorfulWorld.COLORFUL_GLASS_ITEM, ColorfulWorld.COLORFUL_GLASS_PANE_ITEM);
    }
}
