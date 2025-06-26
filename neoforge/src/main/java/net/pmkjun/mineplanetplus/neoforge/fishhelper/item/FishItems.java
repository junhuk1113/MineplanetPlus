package net.pmkjun.mineplanetplus.neoforge.fishhelper.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fishhelper.item.FishItemList;

import java.util.Arrays;

public class FishItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("pyrofishinghelper");
    public static final DeferredItem<?>[] COMMON_FISH = new DeferredItem<?>[FishItemList.COMMMON_FISH_LIST.length];
    public static final DeferredItem<?>[] UNCOMMON_FISH = new DeferredItem<?>[FishItemList.UNCOMMON_FISH_LIST.length];
    public static final DeferredItem<?>[] RARE_FISH = new DeferredItem<?>[FishItemList.RARE_FISH_LIST.length];
    public static final DeferredItem<?>[] EPIC_FISH = new DeferredItem<?>[FishItemList.EPIC_FISH_LIST.length];
    public static final DeferredItem<?>[] LEGENDARY_FISH = new DeferredItem<?>[FishItemList.LEGENDARY_FISH_LIST.length];
    public static final DeferredItem<?>[] MYTHIC_FISH = new DeferredItem<?>[FishItemList.MYTHIC_FISH_LIST.length];
    public static final DeferredItem<Item> QUEST = ITEMS.registerItem("quest", Item::new, new Item.Properties());

    public static void register(){
        int i;
        for(i = 0; i<FishItemList.COMMMON_FISH_LIST.length; i++){
            COMMON_FISH[i] = ITEMS.registerItem("common_"+i, Item::new, new Item.Properties());
        }
        for(i = 0; i<FishItemList.UNCOMMON_FISH_LIST.length; i++){
            UNCOMMON_FISH[i] = ITEMS.registerItem("uncommon_"+i, Item::new, new Item.Properties());
        }
        for(i = 0; i<FishItemList.RARE_FISH_LIST.length; i++){
            RARE_FISH[i] = ITEMS.registerItem("rare_"+i, Item::new, new Item.Properties());
        }
        for(i = 0; i<FishItemList.EPIC_FISH_LIST.length; i++){
            EPIC_FISH[i] = ITEMS.registerItem("epic_"+i, Item::new, new Item.Properties());
        }
        for(i = 0; i<FishItemList.LEGENDARY_FISH_LIST.length; i++){
            LEGENDARY_FISH[i] = ITEMS.registerItem("legendary_"+i, Item::new, new Item.Properties());
        }
        for(i = 0; i<FishItemList.MYTHIC_FISH_LIST.length; i++){
            MYTHIC_FISH[i] = ITEMS.registerItem("mythic_"+i, Item::new, new Item.Properties());
        }

    }
    public static Item getFishItem(ItemStack itemStack){
        String name = itemStack.getHoverName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("minecraft:cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        index = Arrays.stream(FishItemList.COMMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return COMMON_FISH[index].get();

        index = Arrays.stream(FishItemList.UNCOMMON_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return UNCOMMON_FISH[index].get();

        index = Arrays.stream(FishItemList.RARE_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return RARE_FISH[index].get();

        index = Arrays.stream(FishItemList.EPIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return EPIC_FISH[index].get();

        index = Arrays.stream(FishItemList.LEGENDARY_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return LEGENDARY_FISH[index].get();

        index = Arrays.stream(FishItemList.MYTHIC_FISH_LIST).toList().indexOf(name);
        if(index!=-1) return MYTHIC_FISH[index].get();

        return null;
    }
    public static Item getQuestItem(ItemStack itemStack, FishHelperClient client){
        String name = itemStack.getHoverName().getString();
        int index;

        if(!(itemStack.getItem().toString().equals("minecraft:cod"))) return null;
        if(!FishHelperClient.getInstance().data.toggleCustomTexture) return null;

        if (client.deliveryQuests.isFishExistQuestData(name)) return QUEST.asItem();

        return null;
    }

    public static Item getQuestItem(){
        return QUEST.asItem();
    }
}