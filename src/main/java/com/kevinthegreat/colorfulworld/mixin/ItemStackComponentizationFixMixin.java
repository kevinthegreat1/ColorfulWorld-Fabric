package com.kevinthegreat.colorfulworld.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.fix.ItemStackComponentizationFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStackComponentizationFix.class)
public class ItemStackComponentizationFixMixin {
    @Inject(method = "fixBlockEntityData", at = @At(value = "INVOKE", target = "Ljava/lang/String;hashCode()I"), cancellable = true)
    private static <T> void colorfulworld$fixColorfulBlockEntityData(ItemStackComponentizationFix.StackData data, Dynamic<T> dynamic, String blockEntityId, CallbackInfoReturnable<Dynamic<T>> cir) {
        if (blockEntityId.equals("colorfulworld:colorful_block_entity")) {
            data.setComponent("colorfulworld:color", dynamic.get("Color"));
            cir.setReturnValue(dynamic.remove("Color"));
        }
    }
}
