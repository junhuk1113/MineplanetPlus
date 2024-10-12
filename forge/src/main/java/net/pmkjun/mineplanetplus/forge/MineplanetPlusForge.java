package net.pmkjun.mineplanetplus.forge;

import net.minecraftforge.fml.common.Mod;

import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.forge.planetskilltimer.PlanetSkillTimerForge;

@Mod(MineplanetPlus.MOD_ID)
public final class MineplanetPlusForge {
    public MineplanetPlusForge() {
        // Run our common setup.
        MineplanetPlus.init();

        @SuppressWarnings("unused")
        DungeonHelper dungeonHelper = new DungeonHelper();
        
        @SuppressWarnings("unused")
        PlanetSkillTimerForge planetSkillTimer = new PlanetSkillTimerForge();
    }
}
