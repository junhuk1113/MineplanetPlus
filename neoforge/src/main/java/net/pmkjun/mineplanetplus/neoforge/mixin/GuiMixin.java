package net.pmkjun.mineplanetplus.neoforge.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.profiling.Profiler;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
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
    DungeonHelperClient dungeonhelper = DungeonHelperClient.getInstance();
    FishHelperClient fishhelper = FishHelperClient.getInstance();
    PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();
    ServerUtilityClient serverutility = ServerUtilityClient.getInstance();

    @Inject(method = "render", at = {@At("RETURN")}, cancellable = false)
    private void renderMixin(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo info) {
        if(!minecraft.options.hideGui && serverutility.isHereMineplanet()) { //F1 눌렀을 때 모드 gui 렌더링 비활성화
            dungeonhelper.renderEvent(guiGraphics, title, overlayMessageString);
            fishhelper.renderEvent(guiGraphics);
            skilltimer.renderEvent(guiGraphics);
            serverutility.renderEvent(guiGraphics);
        }
    }

    @Inject(method = "renderExperienceLevel", at = {@At("HEAD")}, cancellable  = true)
    public void renderExperienceBarMixin(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo info){
        if(serverutility.isHereMineplanet())
        {
            info.cancel();
        }

    }
}