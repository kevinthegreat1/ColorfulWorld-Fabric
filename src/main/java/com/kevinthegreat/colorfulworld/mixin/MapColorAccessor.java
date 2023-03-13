package com.kevinthegreat.colorfulworld.mixin;

import net.minecraft.block.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MapColor.class)
public interface MapColorAccessor {
    @Accessor
    static MapColor[] getCOLORS() {
        throw new IllegalStateException("Mixin accessor failed to apply");
    }
}
