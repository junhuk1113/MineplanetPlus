package net.pmkjun.mineplanetplus.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.fabric.fishhelper.item.FishItems;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertActivateTime;
import net.pmkjun.mineplanetplus.fishhelper.util.ConvertCooldown;
import net.pmkjun.mineplanetplus.fishhelper.util.FishingRod;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemModelShaper.class)
public class ItemModelsMixin {
    @Shadow
    @Final
    private ModelManager modelManager;
    @Shadow
    public BakedModel getItemModel(Item item) {
        return null;
    }

    private Minecraft mc = Minecraft.getInstance();
    private ItemStack previousMainhandStack;

    @Inject(method = {"getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"},at = {@At("TAIL")}, cancellable = true)
    public void getModelMixin(ItemStack stack, CallbackInfoReturnable<BakedModel> cir){
        Item changed_item;
        List<Component> ItemText;
        String Itemname = null;
        String levelString;
        int levelInt;
        double secondDouble;
        long secondLong;

        cir.cancel();

        ItemText = stack.getTooltipLines(mc.player, TooltipFlag.NORMAL);
        for(Component text : ItemText){
            if(Itemname == null)
                Itemname = text.getString();

            if(!Itemname.equals("지속시간 업그레이드")&&!Itemname.equals("쿨타임 감소")&&!Itemname.contains("토템 리더 |"))
                break;

            if(text.getString().contains("현재 레벨 ➛ ")){
                levelString = text.getString().replace("현재 레벨 ➛ ","");
                levelInt = Integer.parseInt(levelString);
                //System.out.println(Itemname +" : "+ levelString);
                if(Itemname.equals("지속시간 업그레이드")){
                    if(FishHelperClient.getInstance().data.valueTotemActivetime != ConvertActivateTime.asMinute(levelInt))
                    {
                        FishHelperClient.getInstance().data.valueTotemActivetime = ConvertActivateTime.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
                if(Itemname.equals("쿨타임 감소")){
                    if(FishHelperClient.getInstance().data.valueTotemCooldown != ConvertCooldown.asMinute(levelInt))
                    {
                        FishHelperClient.getInstance().data.valueTotemCooldown = ConvertCooldown.asMinute(levelInt);
                        FishHelperClient.getInstance().configManage.save();
                    }
                }
            }
            if(Itemname.contains("토템 리더 |") && text.getString().contains("효과|") && !text.getString().contains("다음 레벨 효과")){
                levelString = text.getString().replace("효과| ", "");
                levelString = levelString.replace(" 초 감소", "");
                secondDouble = Double.parseDouble(levelString);
                secondLong = (long)(secondDouble * 1000);
                if(FishHelperClient.getInstance().data.valueCooldownReduction!=secondLong){
                    FishHelperClient.getInstance().data.valueCooldownReduction = secondLong;
                    FishHelperClient.getInstance().configManage.save();
                }
            }

        }

        ItemStack mainhandStack = mc.player.getMainHandItem();
        if(mainhandStack!=previousMainhandStack){
            previousMainhandStack = mainhandStack;
            //mc.player.sendMessage(Text.literal("MainhandStack 변경됨 : "+mainhandStack.getItem().getTranslationKey()));

            if(mainhandStack.getItem().getDescriptionId().equals("item.minecraft.fishing_rod")){
                FishingRod.updateSpec(mainhandStack);
            }
        }
            

        if((changed_item = FishItems.getFishItem(stack))!=null) stack = new ItemStack((ItemLike) changed_item, stack.getCount());

        BakedModel bakedModel = getItemModel(stack.getItem());
        cir.setReturnValue((bakedModel == null) ? modelManager.getMissingModel() : bakedModel);
    }

}