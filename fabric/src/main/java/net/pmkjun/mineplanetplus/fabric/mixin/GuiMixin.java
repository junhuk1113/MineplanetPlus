package net.pmkjun.mineplanetplus.fabric.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.profiling.Profiler;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public class GuiMixin {

    @Shadow
    private Component title;

    @Shadow
    private Component overlayMessageString;

    private Minecraft minecraft = Minecraft.getInstance();
    DungeonHelperClient dungeonhelper = DungeonHelperClient.getInstance();
    FishHelperClient fishhelper = FishHelperClient.getInstance();
    PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();
    MegaphoneTimerClient megaphonetimer = MegaphoneTimerClient.getInstance();

    @Inject(method = "render", at = {@At("RETURN")}, cancellable = false)
    private void renderMixin(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo info) {
        if(!minecraft.options.hideGui) { //F1 눌렀을 때 모드 gui 렌더링 비활성화
            dungeonhelper.renderEvent(guiGraphics, title, overlayMessageString);
            fishhelper.renderEvent(guiGraphics);
            skilltimer.renderEvent(guiGraphics);
            megaphonetimer.renderEvent(guiGraphics);
        }
    }

    @Inject(method = "renderExperienceBar(Lnet/minecraft/client/gui/GuiGraphics;I)V", at = {@At("HEAD")}, cancellable  = true)
    public void renderExperienceBarMixin(GuiGraphics guiGraphics, int i, CallbackInfo info){
        int l;
        int m;
        if(dungeonhelper.ishereDungeon)
        {
            info.cancel();

            if (this.minecraft.player.experienceLevel > 0 && dungeonhelper.data.toggleVanillaLevelView) {
                Profiler.get().push("expLevel");
                String string = "" + this.minecraft.player.experienceLevel;
                l = (guiGraphics.guiWidth() - this.minecraft.font.width(string)) / 2;
                m = guiGraphics.guiHeight() - 32 + 3 - 18;
                guiGraphics.drawString(this.minecraft.font, string, l + 1, m, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l - 1, m, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m + 1, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m - 1, 0, false);
                guiGraphics.drawString(this.minecraft.font, string, l, m, 8453920, false);
                Profiler.get().pop();
             }
        }
            
    }
}
