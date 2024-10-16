package net.pmkjun.mineplanetplus.fabric.fishhelper.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pmkjun.mineplanetplus.fishhelper.gui.screen.FishHelperConfigScreen;

public class FishHelperModMenu implements ModMenuApi{
    public ConfigScreenFactory<?> getModConfigScreenFactory(){
        return FishHelperConfigScreen::new;
    }

}
