package net.pmkjun.mineplanetplus.forge.dungeonhelper;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.dungeonhelper.gui.screen.SettingsScreen;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.sound.SoundManager;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.input.KeyMappings;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.item.DungeonItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


public class DungeonHelper {
    public static final String MODID = "dungeonhelper";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DungeonHelperClient client;

    public DungeonHelper(){
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        DungeonItems.ITEMS.register(eventBus);

        SoundManager soundManager = new SoundManager();
        soundManager.register();

        KeyMappings keyMapping = new KeyMappings();
        keyMapping.register();

        client = new DungeonHelperClient(soundManager, keyMapping);
        client.init();
    }

    private void setup(final FMLCommonSetupEvent event){
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new SettingsScreen(screen)));
    }
}
