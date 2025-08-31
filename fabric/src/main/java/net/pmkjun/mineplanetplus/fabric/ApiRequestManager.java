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

public class ApiRequestManager {
    private static final String URL = "http://34.19.79.135:8080/TotemServer/findTotemData";
    // HttpClient는 한 번만 만들어서 재사용하는 것이 효율적입니다.
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(1))
            .build();

    private static int tickCounter = 0;
    // 명령어로 이 값을 바꿔서 요청을 켜고 끌 수 있습니다.
    public static boolean shouldSendRequests = true;

    // YourModClient에서 매 틱마다 이 메서드를 호출합니다.
    public static void onClientTick() {
        // 월드에 접속하지 않았거나, 게임이 일시정지 상태면 실행하지 않습니다.
        if (Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()) {
            return;
        }

        // 요청을 보내도록 설정되었을 때만 카운터를 증가시킵니다.
        if (!shouldSendRequests) {
            return;
        }

        tickCounter++;

        // 20틱 = 1초
        if (tickCounter >= 20) {
            tickCounter = 0; // 카운터 초기화
            sendPeriodicRequest(); // 1초마다 요청 보내는 메서드 호출
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

        // 현재 플레이어 이름을 가져옵니다. null 체크는 필수입니다.
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL+"?player_X="+player_X+"&player_Z="+player_Z+"&playerWorld="+encodedPlayerWorld))
                .build();

        // ★★★반드시 비동기로 요청을 보내야 게임이 멈추지 않습니다!★★★
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    // 이 부분은 백그라운드 스레드에서 실행됩니다.
                    String responseBody = response.body();

                    // 마인크래프트 관련 작업(예: 채팅 메시지)은 반드시 메인 스레드에서 처리해야 합니다.
                    // MinecraftClient.getInstance().execute()를 사용해 작업을 예약합니다.
                    Minecraft.getInstance().execute(() -> {
                         //System.out.println("서버로부터 응답을 받았습니다: " + responseBody);
                         Gson gson = new Gson();
                         client.setRemoteTotemDataList(gson.fromJson(responseBody,
                                 new TypeToken<ArrayList<TotemData>>() {}.getType()));
                    });
                })
                .exceptionally(error -> {
                    System.err.println("요청 실패: " + error.getMessage());
                    return null;
                });
    }
}