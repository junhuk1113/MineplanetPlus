package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperMod;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;
import net.pmkjun.mineplanetplus.planetskilltimer.PlanetSkillTimerClient;
import net.pmkjun.mineplanetplus.planetskilltimer.file.Skill;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public abstract class ChatMixin {
    private static final int COMMON = 0;
    private static final int UNCOMMON = 1;
    private static final int RARE = 2;
    private static final int EPIC = 3;
    private static final int LEGENDARY = 4;
    private static final int MYTHIC = 5;

    private final FishHelperClient fishhelper = FishHelperClient.getInstance();
    private final PlanetSkillTimerClient skilltimer = PlanetSkillTimerClient.getInstance();
    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/network/chat/Component;)V")
    private void addMessageMixin(Component message, CallbackInfo ci) {
        //피시헬퍼
        if(fishhelper.data.toggleChattinglog)
            FishHelperMod.LOGGER.info(message.getString());
        
        if((message.getString().contains("\uE2F8 ") && (message.getString().contains("을(를) 낚았습니다.")||message.getString().contains("You caught a")))||
        (message.getString().contains("\uE2F8 ") && message.getString().contains("로 변환되었습니다."))){
        FISH:
        {
            for(String fishName : FishItemList.MYTHIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[MYTHIC]++;
                    this.fishhelper.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.LEGENDARY_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[LEGENDARY]++;
                    this.fishhelper.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.EPIC_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[EPIC]++;
                    this.fishhelper.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.RARE_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[RARE]++;
                    this.fishhelper.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.UNCOMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[UNCOMMON]++;
                    this.fishhelper.configManage.save();
                    break FISH;
                }
            }
            for(String fishName : FishItemList.COMMMON_FISH_LIST){
                if(message.getString().contains(fishName)){
                    this.fishhelper.data.fish_Count[COMMON]++;
                    this.fishhelper.configManage.save();
                }
            }
        }
        }

        //스킬타이머
		if(message.getString().contains(" 발동되었습니다!") && !message.getString().contains("|")){
			//System.out.println("변수 초기화 됨");
			for (int i = 0; i < Skill.list.length ; i++)
			{
				if(message.getString().contains(Skill.list[i])){
					//System.out.println(Skill.list[i]+" 발동감지!");
					skilltimer.updateLastSkilltime(i);
				}
			}
		}
    }
}