package net.pmkjun.mineplanetplus.dungeonhelper.file;

import net.pmkjun.mineplanetplus.MPPSettings;
import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;

import java.io.File;
import java.io.FileReader;

public class Settings extends MPPSettings{
    public Settings() {
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
            return "\\MineplanetPlus\\dungeonhelper.json";
        } else {
            return "/MineplanetPlus/dungeonhelper.json";
        }
    }

    public void save() {
        save(DungeonHelperClient.getInstance().data);
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