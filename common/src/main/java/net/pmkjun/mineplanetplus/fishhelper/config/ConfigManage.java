package net.pmkjun.mineplanetplus.fishhelper.config;

import net.pmkjun.mineplanetplus.MPPSettings;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.file.Data;

import java.io.*;

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
            return "\\MineplanetPlus\\fishhelper.json";
        } else {
            return "/MineplanetPlus/fishhelper.json";
        }
    }

    public void save() {
        save(FishHelperClient.getInstance().data);
    }

    public Data load() {
        File file = new File(System.getProperty("user.dir") + getDataFilePath());

        try {
            if(!file.exists()) {
                save(new Data());
            }

            FileReader reader = new FileReader(System.getProperty("user.dir") + getDataFilePath());
            Data data = gson.fromJson(reader, Data.class);

            return data;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
