package net.pmkjun.mineplanetplus.fishhelper;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public class FishHelperMod
{
	public static final String MOD_ID = "pyrofishinghelper";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static FishHelperClient client;
	public static void init() {
		client = new FishHelperClient();
		client.init();
	}
}
