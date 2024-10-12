package net.pmkjun.mineplanetplus.forge.mixin;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForgeGui.class)
public class GuiMixin extends Gui {
    private static final DungeonHelperClient dungeonhelper = DungeonHelperClient.getInstance();
    private static final FishHelperClient fishhelper = FishHelperClient.getInstance();
    private static final PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();

    public GuiMixin(Minecraft minecraft, ItemRenderer itemRenderer) {
        super(minecraft, itemRenderer);
    }

    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V", at = {@At("HEAD")}, cancellable = false)
    public void renderMixin(GuiGraphics guiGraphics, float tickDelta, CallbackInfo info) {
        dungeonhelper.renderEvent(guiGraphics, title, overlayMessageString);
        fishhelper.renderEvent(guiGraphics);
        skilltimer.renderEvent(guiGraphics);
    }

    @Inject(method = "renderExperience(ILnet/minecraft/client/gui/GuiGraphics;)V", at = {@At("HEAD")}, cancellable  = true, remap = false)
    protected void renderExperienceMixin(int x, GuiGraphics guiGraphics, CallbackInfo info){
        int l;
        int m;
        if(dungeonhelper.ishereDungeon)
        {
            info.cancel();

            if (this.minecraft.player.experienceLevel > 0 && dungeonhelper.data.toggleVanillaLevelView) {
                this.minecraft.getProfiler().push("expLevel");
                String string = "" + this.minecraft.player.experienceLevel;
                l = (guiGraphics.guiWidth() - this.minecraft.font.width(string)) / 2;
                m = guiGraphics.guiHeight() - 32 + 3 + 2;
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