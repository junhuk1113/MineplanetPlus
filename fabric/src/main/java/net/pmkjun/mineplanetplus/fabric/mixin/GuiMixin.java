package net.pmkjun.mineplanetplus.fabric.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow
    private Component title;

    @Shadow
    private Component overlayMessageString;

    private Minecraft minecraft = Minecraft.getInstance();
    private static final DungeonHelperClient dungeonhelper = DungeonHelperClient.getInstance();
    private static final FishHelperClient fishhelper = FishHelperClient.getInstance();
    private static final PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();
    private static final MegaphoneTimerClient megaphonetimer = MegaphoneTimerClient.getInstance();

    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V", at = {@At("RETURN")}, cancellable = false)
    private void renderMixin(GuiGraphics guiGraphics, float tickDelta, CallbackInfo info) {
        dungeonhelper.renderEvent(guiGraphics, title, overlayMessageString);
        fishhelper.renderEvent(guiGraphics);
        skilltimer.renderEvent(guiGraphics);
        megaphonetimer.renderEvent(guiGraphics);
    }

    @Inject(method = "renderExperienceBar(Lnet/minecraft/client/gui/GuiGraphics;I)V", at = {@At("HEAD")}, cancellable  = true)
    public void renderExperienceBarMixin(GuiGraphics guiGraphics, int i, CallbackInfo info){
        int l;
        int m;
        if(dungeonhelper.ishereDungeon)
        {
            info.cancel();

            if (this.minecraft.player.experienceLevel > 0 && dungeonhelper.data.toggleVanillaLevelView) {
                this.minecraft.getProfiler().push("expLevel");
                String string = "" + this.minecraft.player.experienceLevel;
                l = (guiGraphics.guiWidth() - this.minecraft.font.width(string)) / 2;
                m = guiGraphics.guiHeight() - 32 + 3 - 18;
                guiGraphics.drawString(this.minecraft.font, string, l + 1, m, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l - 1, m, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m + 1, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m - 1, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m, 8453920, false);
                this.minecraft.getProfiler().pop();
             }
        }
            
    }
}
