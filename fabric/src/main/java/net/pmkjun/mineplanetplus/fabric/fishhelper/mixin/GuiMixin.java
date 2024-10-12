package net.pmkjun.mineplanetplus.fabric.fishhelper.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Gui.class})
public class GuiMixin {
    @Inject(method = {"render(Lnet/minecraft/client/gui/GuiGraphics;F)V"}, at = {@At("RETURN")} ,cancellable = false)
        private void renderMixin(GuiGraphics context, float tickDelta, CallbackInfo info) {
            FishHelperClient.getInstance().renderEvent(context);
        }
}