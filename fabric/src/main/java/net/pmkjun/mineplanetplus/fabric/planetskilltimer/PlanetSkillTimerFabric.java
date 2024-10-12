package net.pmkjun.mineplanetplus.fabric.planetskilltimer;

import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimer;
import net.pmkjun.mineplanetplus.fabric.planetskilltimer.input.KeyMappings;

public class PlanetSkillTimerFabric{
    public void init() {
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        PlanetSkillTimer.init();
    }
}