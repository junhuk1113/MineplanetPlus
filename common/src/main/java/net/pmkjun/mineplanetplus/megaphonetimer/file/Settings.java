package net.pmkjun.mineplanetplus.megaphonetimer.file;

import net.pmkjun.mineplanetplus.dungeonhelper.DungeonHelperClient;
import net.pmkjun.mineplanetplus.megaphonetimer.MegaphoneTimerClient;

import java.io.*;

public class Settings {
    private static String DATA_DIRECTORY_PATH = "\\MegaphoneTimer";
    private static String DATA_FILE_PATH = DATA_DIRECTORY_PATH + "\\setting.data";

    public Settings() {
        if(!System.getProperty("os.name").contains("Windows")){
            DATA_DIRECTORY_PATH = "/MegaphoneTimer";
            DATA_FILE_PATH = DATA_DIRECTORY_PATH + "/Setting.data";
        }
    }

    public void save() {
        save(MegaphoneTimerClient.getInstance().data);
    }

    public void save(Data data) {
        File file = new File(System.getProperty("user.dir") + DATA_FILE_PATH);
        File directory = new File(System.getProperty("user.dir") + DATA_DIRECTORY_PATH);

        try {
            if(!file.exists()) {
                directory.mkdirs();
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(data);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Data load() {
        File file = new File(System.getProperty("user.dir") + DATA_FILE_PATH);

        try {
            if(!file.exists()) {
                save(new Data());
            }

            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Data data = (Data) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            return data;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}