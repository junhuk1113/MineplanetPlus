package net.pmkjun.mineplanetplus.neoforge.planetskilltimer;

import net.neoforged.bus.api.IEventBus;
import net.pmkjun.mineplanetplus.neoforge.planetskilltimer.input.KeyMappings;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimer;
import net.pmkjun.mineplanetplus.planetskilltimer.config.SkillTimerConfigScreen;

public class PlanetSkillTimerForge {
    public PlanetSkillTimerForge(IEventBus modEventBus) {

        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register(modEventBus);

        PlanetSkillTimer.init();
    }
}
