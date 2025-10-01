package net.pmkjun.mineplanetplus.fabric;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.file.TotemData;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;

public class ApiRefreshManager {
    private static final String URL = "http://34.19.79.135:8080/TotemServer/findTotemData";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(1))
            .build();

    private static int tickCounter = 0;

    public static boolean shouldSendRequests = true;

    public static void onClientTick() {
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()) {
            return;
        }

        if (!shouldSendRequests) {
            return;
        }

        tickCounter++;

        // 20틱 = 1초
        if (tickCounter >= 20) {
            tickCounter = 0; // 카운터 초기화
            sendPeriodicRequest();
        }
    }

    private static void sendPeriodicRequest() {
        FishHelperClient client = FishHelperClient.getInstance();

        double player_X = Minecraft.getInstance().player.getX();
        double player_Z = Minecraft.getInstance().player.getZ();
        String playerWorld, encodedPlayerWorld;
        try{
            playerWorld = client.getCurrentWorld();
            encodedPlayerWorld = URLEncoder.encode(playerWorld, StandardCharsets.UTF_8);
        }
        catch (NullPointerException e){
            //System.out.println("월드 정보를 불러올 수 없습니다!");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL+"?player_X="+player_X+"&player_Z="+player_Z+"&playerWorld="+encodedPlayerWorld))
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    String responseBody = response.body();

                    Minecraft.getInstance().execute(() -> {
                         //System.out.println("서버로부터 응답을 받았습니다: " + responseBody);
                         Gson gson = new Gson();
                         client.setRemoteTotemDataList(gson.fromJson(responseBody,
                                 new TypeToken<ArrayList<TotemData>>() {}.getType()));
                    });
                })
                .exceptionally(error -> {
                    //System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }
}