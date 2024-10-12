package net.pmkjun.mineplanetplus.dungeonhelper.sound;

public interface ISoundManager {

    void register();

    void playSound(String soundName, float volume);
}
