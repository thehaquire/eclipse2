package org.eclipse.eclipse.mixin;

import net.minecraft.client.Minecraft;
import org.eclipse.eclipse.module.Handle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/**
 * An example mixin using SpongePowered's Mixin library and ObjectWeb ASM.
 *
 * @see Inject
 * @see Mixin
 */
@Mixin(value = Minecraft.class)
public class MinecraftMixin {

    @Shadow private int leftClickCounter;

    @Inject(method = "clickMouse", at = @At("HEAD"))
    private void clickMouseAfter(final CallbackInfo ci) {
        if (Handle.isModuleEnabled("Combat", "Hit Delay Fix")) {
            leftClickCounter = 0;
        }
    }
}