package net.pmkjun.mineplanetplus.fabric.planetskilltimer.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pmkjun.mineplanetplus.planetskilltimer.config.ConfigScreen;

public class SkillTimerModMenu implements ModMenuApi {
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return ConfigScreen::new;
    }
}