package net.pmkjun.mineplanetplus;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MPPSettings {
    private final String DATA_DIRECTORY_PATH;
    private final String DATA_FILE_PATH;
    protected Gson gson = new Gson();

    public MPPSettings(String dataDirectoryPath, String dataFilePath) {
        DATA_DIRECTORY_PATH = dataDirectoryPath;
        DATA_FILE_PATH = dataFilePath;
    }

    public <T> void save(T data) {
        File directory = new File(System.getProperty("user.dir") + DATA_DIRECTORY_PATH);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(System.getProperty("user.dir") + DATA_FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
