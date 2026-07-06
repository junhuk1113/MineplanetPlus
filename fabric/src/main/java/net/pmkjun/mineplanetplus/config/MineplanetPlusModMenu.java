package net.pmkjun.mineplanetplus.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pmkjun.mineplanetplus.gui.SettingsScreen;

public class MineplanetPlusModMenu implements ModMenuApi{
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return SettingsScreen::new;
    }

}
