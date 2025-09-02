package net.pmkjun.mineplanetplus.fishhelper;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiRequestManager {
    private static final String URL = "http://34.19.79.135:8080/TotemServer/addTotemData";
    private static final String URL_REDUCE_COOLDOWN = "http://34.19.79.135:8080/TotemServer/reduceTotemCooldown";

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

        // 현재 플레이어 이름을 가져옵니다. null 체크는 필수입니다.
        HttpRequest request = HttpRequest.newBuilder()
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

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();
                    mc.execute(() -> {
                        //mc.player.displayClientMessage(Component.literal("토템 데이터를 성공적으로 전송했습니다!"),false);
                        //System.out.println("서버로부터 응답을 받았습니다: " + responseBody);
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

        // 현재 플레이어 이름을 가져옵니다. null 체크는 필수입니다.
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_REDUCE_COOLDOWN + "?username=" + username
                        + "&lastTotemCooldownTime=" + lastTotemCooldownTime))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    // 이 부분은 백그라운드 스레드에서 실행됩니다.
                    String responseBody = response.body();

                    mc.execute(() -> {
                        //mc.player.displayClientMessage(Component.literal("토템 쿨타임 감소를 성공적으로 적용했습니다!"),false);
                        //System.out.println("서버로부터 응답을 받았습니다: " + responseBody);
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }
}