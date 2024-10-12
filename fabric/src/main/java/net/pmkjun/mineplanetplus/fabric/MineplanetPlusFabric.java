package net.pmkjun.mineplanetplus.fabric;

import net.fabricmc.api.ModInitializer;

import net.pmkjun.mineplanetplus.MineplanetPlus;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.pmkjun.mineplanetplus.fabric.fishhelper.FishHelperFabric;
import net.pmkjun.mineplanetplus.fabric.planetskilltimer.PlanetSkillTimerFabric;

public final class MineplanetPlusFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DungeonHelper dungeonhelper = new DungeonHelper();
        FishHelperFabric fishhelper = new FishHelperFabric();
        PlanetSkillTimerFabric skilltimer = new PlanetSkillTimerFabric();
        
        MineplanetPlus.init();
        dungeonhelper.init();
        fishhelper.init();
        skilltimer.init();
    }
}
