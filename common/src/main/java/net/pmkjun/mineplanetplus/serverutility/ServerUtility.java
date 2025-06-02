package net.pmkjun.mineplanetplus.serverutility;

public class ServerUtility {
    public static final String MOD_ID = "serverutility";

	public static ServerUtilityClient client;
	public static void init() {
		client = new ServerUtilityClient();
	}
}
