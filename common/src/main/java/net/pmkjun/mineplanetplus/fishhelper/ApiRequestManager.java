package net.pmkjun.mineplanetplus.fishhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.util.ShareMode;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiRequestManager {
    Minecraft mc = Minecraft.getInstance();
    FishHelperClient client = FishHelperClient.getInstance();

    private static final String URL = "http://34.19.79.135:8080/TotemServer/addTotemData";
    private static final String URL_REDUCE_COOLDOWN = "http://34.19.79.135:8080/TotemServer/reduceTotemCooldown";
    private static final String URL_UPDATE_SKILL = "http://34.19.79.135:8080/TotemServer/updateTotemSkill";
    private static final String URL_UPDATE_SHARE_PERCENTAGE = "http://34.19.79.135:8080/TotemServer/updateShareTotemPercentage";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(1))
            .build();

    public static String uploadTotemData(){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();
        
        String username = mc.player.getName().getString();
        int valueTotemCooldown = client.data.currentValueTotemCooldown;
        int valueTotemActivetime = client.data.currentValueTotemActivetime;
        int valueTotemRange = client.getTotemRange();
        long lastTotemtime = client.data.lastTotemTime;
        long lastTotemCooldownTime = client.data.lastTotemCooldownTime;
        int totem_X = client.getTotemposX();
        int totem_Z = client.getTotemposZ();
        String playerWorld = client.getCurrentWorld();
        String encodedPlayerWorld = URLEncoder.encode(playerWorld, StandardCharsets.UTF_8);
        String shareTotemPercentage = client.data.shareTotemPercentage;
        boolean isMythicalWaterActive = client.data.isMythicalWaterActive;
        boolean isExpBoosterActive = client.data.isExpBoosterActive;
        boolean isOverHotspotActive = client.data.isOverHotspotActive;
        boolean isTreasureHunterActive = client.data.isTreasureHunterActive;
        boolean isEntropyHoarder = client.data.isEntropyHoarder;
        String totemSlotString = client.data.totemSlotString;

        HttpRequest request;

        if(client.data.toggleShareTotemData == ShareMode.ON) { // ShareMode.ON
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL + "?username=" + username
                            + "&valueTotemCooldown=" + valueTotemCooldown
                            + "&valueTotemActiveTime=" + valueTotemActivetime
                            + "&valueTotemRange=" + valueTotemRange
                            + "&lastTotemtime=" + lastTotemtime
                            + "&lastTotemCooldownTime=" + lastTotemCooldownTime
                            + "&totem_X=" + totem_X
                            + "&totem_Z=" + totem_Z
                            + "&totemWorld=" + encodedPlayerWorld
                            + "&shareTotemPercentage=" + shareTotemPercentage
                            + "&isMythicalWaterActive=" + isMythicalWaterActive
                            + "&isExpBoosterActive=" + isExpBoosterActive
                            + "&isOverHotspotActive=" + isOverHotspotActive
                            + "&isTreasureHunterActive=" + isTreasureHunterActive
                            + "&isEntropyHoarder=" + isEntropyHoarder
                            + "&totemSlotString=" + totemSlotString))
                    .build();
        }
        else { // ShareMode.LIMIT
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL + "?username=" + username
                            + "&valueTotemCooldown=" + valueTotemCooldown
                            + "&valueTotemActiveTime=" + valueTotemActivetime
                            + "&valueTotemRange=" + valueTotemRange
                            + "&lastTotemtime=" + lastTotemtime
                            + "&lastTotemCooldownTime=" + lastTotemCooldownTime
                            + "&totem_X=" + totem_X
                            + "&totem_Z=" + totem_Z
                            + "&totemWorld=" + encodedPlayerWorld))
                    .build();
        }
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();
                    mc.execute(() -> {
                        //mc.player.displayClientMessage(Component.literal("토템 데이터를 성공적으로 전송했습니다!"),false);
                        System.out.println(responseBody);
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
        return "";
    }

    public static void reduceTotemCooldown(){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();

        String username = mc.player.getName().getString();
        long lastTotemCooldownTime = client.data.lastTotemCooldownTime;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_REDUCE_COOLDOWN + "?username=" + username
                        + "&lastTotemCooldownTime=" + lastTotemCooldownTime))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();

                    mc.execute(() -> {
                        //mc.player.displayClientMessage(Component.literal("토템 쿨타임 감소를 성공적으로 적용했습니다!"),false);
                        System.out.println(responseBody);
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }

    public static void updateTotemSkill(){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();

        String username = mc.player.getName().getString();
        boolean isMythicalWaterActive = client.data.isMythicalWaterActive;
        boolean isExpBoosterActive = client.data.isExpBoosterActive;
        boolean isOverHotspotActive = client.data.isOverHotspotActive;
        boolean isTreasureHunterActive = client.data.isTreasureHunterActive;
        boolean isEntropyHoarder = client.data.isEntropyHoarder;
        String totemSlotString = client.data.totemSlotString;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_UPDATE_SKILL + "?username=" + username
                        + "&isMythicalWaterActive=" + isMythicalWaterActive
                        + "&isExpBoosterActive=" + isExpBoosterActive
                        + "&isOverHotspotActive=" + isOverHotspotActive
                        + "&isTreasureHunterActive=" + isTreasureHunterActive
                        + "&isEntropyHoarder=" + isEntropyHoarder
                        + "&totemSlotString=" + totemSlotString))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();

                    mc.execute(() -> {
                        System.out.println(responseBody);
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }

    public static void updateShareTotemPercentage(){
        Minecraft mc = Minecraft.getInstance();
        FishHelperClient client = FishHelperClient.getInstance();

        String username = mc.player.getName().getString();
        String shareTotemPercentage = client.data.shareTotemPercentage;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_UPDATE_SHARE_PERCENTAGE + "?username=" + username
                        + "&shareTotemPercentage=" + shareTotemPercentage))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();

                    mc.execute(() -> {
                        System.out.println(responseBody);
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }
}