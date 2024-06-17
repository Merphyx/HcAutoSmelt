package de.myronx.hcfastsmelt.mixin;

import de.myronx.hcfastsmelt.HcFastSmelt;
import de.myronx.hcfastsmelt.config.HcFastSmeltConfig;

import net.minecraft.client.gui.screen.ingame.HandledScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Unique
    private boolean keyHandled = false;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        String screenTitle = screen.getTitle().getString();

        if (!keyHandled && HcFastSmelt.keyBinding.matchesKey(keyCode, scanCode) && screenTitle.equals("Schmelzen")) {
            if (HcFastSmeltConfig.hotbartoggle) {
                HcFastSmelt.clickSlots(89);
            } else {
                HcFastSmelt.clickSlots(80);
            }
            keyHandled = true;
            cir.setReturnValue(true);
        }
    }
}