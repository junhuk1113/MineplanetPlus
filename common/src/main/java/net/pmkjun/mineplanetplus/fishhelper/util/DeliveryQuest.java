package net.pmkjun.mineplanetplus.fishhelper.util;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliveryQuest {
    private final List<List<Component>> questTooltipList = new ArrayList<>();
    private final List<HashMap<String,Integer>> questDataList = new ArrayList<>();
    private List<Component> lastSelectedQuestTooltip;
    private HashMap<String,Integer> lastSelectedQuestData;

    public void addLastSelectedQuest(List<Component> questTooltip) {
        int index = findMatchQuestTooltip(questTooltip);
        if(index!=-1){
            removeQuestData(index);
            return;
        }

        lastSelectedQuestTooltip = questTooltip;
        // 새 퀘스트의 데이터 파싱
        HashMap<String,Integer> newQuestData = new HashMap<>();
        for(Component text : questTooltip) {
            List<Component> textList = text.toFlatList();
            try {
                if (textList.get(0).getString().equals("- ")) {
                    newQuestData.put(textList.get(1).getString().trim(),
                            Integer.parseInt(textList.get(3).getString().replace("x","")));
                }
            }
            catch (RuntimeException ignored) {
            }
        }
        lastSelectedQuestData = newQuestData;
    }

    private void removeQuestData(int questIndex){
        questDataList.remove(questIndex);
        questTooltipList.remove(questIndex);
        lastSelectedQuestData = null;
        lastSelectedQuestTooltip = null;
    }

    public int findMatchQuestTooltip(List<Component> questTooltip) {
        return questTooltipList.indexOf(questTooltip);
    }

    public void pushQuestData() {
        if(lastSelectedQuestData==null){
            return;
        }
        questDataList.add(lastSelectedQuestData);
        questTooltipList.add(lastSelectedQuestTooltip);
    }
    public void popQuestData() {
        questDataList.remove(lastSelectedQuestData);
        questTooltipList.remove(lastSelectedQuestTooltip);
    }

    public boolean isFishExistQuestData(String fishName){
        for(HashMap<String,Integer> questData : questDataList){
            if(questData.containsKey(fishName)){
                return true;
            }
        }
        return false;
    }

    public void resetQuestData(){
        questDataList.clear();
        questTooltipList.clear();
        lastSelectedQuestData = null;
        lastSelectedQuestTooltip = null;
    }
}