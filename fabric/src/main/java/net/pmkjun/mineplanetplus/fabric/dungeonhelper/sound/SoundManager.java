package net.pmkjun.mineplanetplus.fabric.dungeonhelper.sound;

import net.pmkjun.mineplanetplus.dungeonhelper.sound.ISoundManager;
import net.pmkjun.mineplanetplus.fabric.dungeonhelper.DungeonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundManager implements ISoundManager {

    public static final SoundEvent TUTELARY_SOUND = SoundEvent.createVariableRangeEvent(new ResourceLocation(DungeonHelper.MODID, "tutelary_sound"));

    public void register() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(DungeonHelper.MODID, "tutelary_sound"), TUTELARY_SOUND);
    }

    @Override
    public void playSound(String soundName, float volume) {
        Minecraft mc = Minecraft.getInstance();

        SoundEvent sound;
        switch (soundName) {
            case "tutelary_sound":
                sound = TUTELARY_SOUND;
                break;

            default:
                return;
        }

        assert mc.player != null;
        mc.player.level().playSound(mc.player, mc.player.getX(), mc.player.getY(), mc.player.getZ(), sound, SoundSource.PLAYERS, volume, 1f);
    }
}
