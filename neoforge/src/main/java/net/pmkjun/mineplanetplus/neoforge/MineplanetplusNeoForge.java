package net.pmkjun.mineplanetplus.neoforge;

import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.neoforged.fml.common.Mod;
import net.pmkjun.mineplanetplus.neoforge.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.neoforge.fishhelper.PyrofishingHelperForge;
import net.pmkjun.mineplanetplus.neoforge.planetskilltimer.PlanetSkillTimerForge;

@Mod(MineplanetPlus.MOD_ID)
public final class MineplanetplusNeoForge {
    public MineplanetplusNeoForge() {
        // Run our common setup.
        DungeonHelper dungeonhelper = new DungeonHelper();
        PyrofishingHelperForge fishhelper = new PyrofishingHelperForge();
        PlanetSkillTimerForge skilltimer = new PlanetSkillTimerForge();

        MineplanetPlus.init();
    }
}
