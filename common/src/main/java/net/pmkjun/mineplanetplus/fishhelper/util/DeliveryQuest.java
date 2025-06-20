package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.List;

public class DeliveryQuest {
    private final List<Component> questTooltip;
    private final HashMap<String,Integer> questData = new HashMap<>();
    public boolean isNotStarted = false;
    public DeliveryQuest(List<Component> questTooltip){
        this.questTooltip = questTooltip;
        for(Component text : questTooltip){
            List<Component> textList = text.toFlatList();
            try {
                if (textList.get(0).getString().equals("- ")) {
                    questData.put(textList.get(1).getString().trim(), Integer.parseInt(textList.get(3).getString().replace("x","")));
                }
            }
            catch (RuntimeException ignored){

            }
        }
        System.out.println(questData);

    }
    public List<Component> getQuestTooltip(){
        return questTooltip;
    }
    public HashMap<String,Integer> getQuestData(){
        return questData;
    }
}
