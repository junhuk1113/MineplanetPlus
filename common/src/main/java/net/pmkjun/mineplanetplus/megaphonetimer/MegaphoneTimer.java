package net.pmkjun.mineplanetplus.megaphonetimer;

public class MegaphoneTimer {
    public static final String MOD_ID = "megaphonetimer";

	public static MegaphoneTimerClient client;
	public static void init() {
		client = new MegaphoneTimerClient();
	}
}
