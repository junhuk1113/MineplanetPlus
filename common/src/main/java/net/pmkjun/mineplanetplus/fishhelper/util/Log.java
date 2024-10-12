package net.pmkjun.mineplanetplus.fishhelper.util;

import com.mojang.logging.LogUtils;

import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;

import org.slf4j.Logger;

public class Log {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FishHelperClient client = FishHelperClient.getInstance();
    public void print(String logString){
        if(client.data.toggleLog)
            LOGGER.info(logString);
    }
}
