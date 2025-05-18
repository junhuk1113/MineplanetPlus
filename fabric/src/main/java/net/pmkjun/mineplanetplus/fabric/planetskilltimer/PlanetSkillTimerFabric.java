package net.pmkjun.mineplanetplus.fabric.planetskilltimer;

import net.pmkjun.mineplanetplus.fabric.planetskilltimer.input.KeyMappings;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimer;

public class PlanetSkillTimerFabric{
    public void init() {
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        PlanetSkillTimer.init();
    }
}