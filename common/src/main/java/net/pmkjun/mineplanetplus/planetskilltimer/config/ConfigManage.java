package net.pmkjun.mineplanetplus.planetskilltimer.config;

import net.pmkjun.mineplanetplus.MPPSettings;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Data;

import java.io.File;
import java.io.FileReader;

public class ConfigManage extends MPPSettings {
    public ConfigManage() {
        super(getDirectoryPath(), getDataFilePath());
    }

    private static String getDirectoryPath() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return "\\MineplanetPlus";
        } else {
            return "/MineplanetPlus";
        }
    }

    private static String getDataFilePath() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return "\\MineplanetPlus\\planetskilltimer.json";
        } else {
            return "/MineplanetPlus/planetskilltimer.json";
        }
    }

    public void save() {
        save(PlanetSkillTimerClient.getInstance().data);
    }

    public Data load() {
        File file = new File(System.getProperty("user.dir") + getDataFilePath());

        try {
            if(!file.exists()) {
                save(new Data());
            }

            FileReader reader = new FileReader(System.getProperty("user.dir") + getDataFilePath());

            return gson.fromJson(reader, Data.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}