package net.pmkjun.mineplanetplus.fabric.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.pmkjun.mineplanetplus.SettingsScreen;

public class MineplanetPlusModMenu implements ModMenuApi{
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return SettingsScreen::new;
    }

}
