package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;
import net.pmkjun.mineplanetplus.fishhelper.item.FishType;

import java.util.ArrayList;

public class FishCache {
    private String fishName;
    private boolean isWaiting = false;
    private int tick_count = 0;

    private static final long CACHE_TIMEOUT_MS = 200;

    private static final Minecraft mc = Minecraft.getInstance();

    public FishCache(){

    }
    public void tick() {
        // caughtTime이 0이면 타이머가 비활성화 상태이므로 아무것도 하지 않음
        if (!isWaiting) {
            return;
        }

        // 현재 시간과 아이템을 낚은 시간의 차이를 계산

        // 경과 시간이 설정된 타임아웃을 초과하면 캐시를 초기화
        if (tick_count >=2) {
            //System.out.println("FishCache 타임아웃! 캐시를 비웁니다."); // 디버깅용 메시지
            mc.player.displayClientMessage(Component.literal("FishCache 타임아웃! 캐시를 비웁니다."),false);
            this.clearCache();
        }
        tick_count++;
    }

    public void addFish(String fishname){
        if(fishName != null) clearCache();
        this.fishName = fishname;
    }

    public void catchFish(String fishname, int previous_count, int current_count){
        int count = current_count - previous_count;

        if(fishName.equals(fishname)){
            if(isWaiting && count == 1){
                addFishCount(fishname);
                clearCache();
            }
            else if(previous_count != 63 && count == 1){
                clearCache();
            }
            else if(previous_count == 63 && count == 1){
                isWaiting = true;
            }
            else if(count == 2){
                addFishCount(fishname);
                clearCache();
            }
        }
    }
    public void catchFish(String fishname, int fish_count){
        if(fishName.equals(fishname)){
            if(isWaiting && fish_count == 1){
                addFishCount(fishname);
                clearCache();
            }
            else if(fish_count == 1){
                clearCache();
            }
            else if(fish_count == 2){
                addFishCount(fishname);
            }
        }
    }

    private void clearCache(){
        fishName = null;
        isWaiting = false;
    }

    private void addFishCount(String fishname){
        int COMMON = 0;
        int UNCOMMON = 1;
        int RARE = 2;
        int EPIC = 3;
        int LEGENDARY = 4;
        int MYTHIC = 5;
        FishHelperClient fishhelper = FishHelperClient.getInstance();
        mc.player.displayClientMessage(Component.literal("디바인이 적용되었습니다!"),false);

        FishType fishType = FishItemList.getFishType(fishname);

        if(fishType == FishType.MYTHIC){
            fishhelper.data.fish_Count[MYTHIC]++;
            fishhelper.configManage.save();
        }
        else if(fishType == FishType.LEGENDARY){
            fishhelper.data.fish_Count[LEGENDARY]++;
            fishhelper.configManage.save();
        }
        else if(fishType == FishType.EPIC){
            fishhelper.data.fish_Count[EPIC]++;
            fishhelper.configManage.save();
        }
        else if(fishType == FishType.RARE){
            fishhelper.data.fish_Count[RARE]++;
            fishhelper.configManage.save();
        }
        else if(fishType == FishType.UNCOMMON){
            fishhelper.data.fish_Count[UNCOMMON]++;
            fishhelper.configManage.save();
        }
        else if(fishType == FishType.COMMON){
            fishhelper.data.fish_Count[COMMON]++;
            fishhelper.configManage.save();
        }
    }
}
