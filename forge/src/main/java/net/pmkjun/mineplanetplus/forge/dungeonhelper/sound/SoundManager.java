package net.pmkjun.mineplanetplus.forge.dungeonhelper.sound;

import net.pmkjun.mineplanetplus.dungeonhelper.sound.ISoundManager;
import net.pmkjun.mineplanetplus.forge.dungeonhelper.DungeonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundManager implements ISoundManager {
    public static final DeferredRegister<SoundEvent> DUNGEON_HELPER_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DungeonHelper.MODID);
    public static final RegistryObject<SoundEvent> TUTELARY_SOUND = registerSoundEvent("tutelary_sound");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return DUNGEON_HELPER_SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DungeonHelper.MODID, name)));
    }

    @Override
    public void register() {
        DUNGEON_HELPER_SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Override
    public void playSound(String soundName, float volume) {
        Minecraft mc = Minecraft.getInstance();

        RegistryObject<SoundEvent> sound;
        switch (soundName) {
            case "tutelary_sound":
                sound = TUTELARY_SOUND;
                break;

            default:
                return;
        }

        assert mc.player != null;
        mc.player.level().playSound(mc.player, mc.player.getX(), mc.player.getY(), mc.player.getZ(), sound.get(), SoundSource.PLAYERS, volume, 1f);
    }
}
