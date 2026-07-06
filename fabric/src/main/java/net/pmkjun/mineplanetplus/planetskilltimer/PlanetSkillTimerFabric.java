package net.pmkjun.mineplanetplus.planetskilltimer;

import net.pmkjun.mineplanetplus.planetskilltimer.input.KeyMappings;

public class PlanetSkillTimerFabric{
    public void init() {
        KeyMappings keyMappings = new KeyMappings();
        keyMappings.register();

        PlanetSkillTimer.init();
    }
}