package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.pmkjun.mineplanetplus.fishhelper.ApiRequestManager;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class ItemPickupMixin {
	private static final Logger LOGGER = LogManager.getLogger("ItemPickupMixin");
	private final FishHelperClient client = FishHelperClient.getInstance();
	private final Minecraft mc = Minecraft.getInstance();
	private final ServerUtilityClient serverutility = ServerUtilityClient.getInstance();
	@Shadow
	private ItemStack carried;
	@Inject(method = "clicked", at = @At("RETURN"))
	private void onSlotClick(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
		if (player instanceof LocalPlayer && serverutility.isHereMineplanet()) {
			//if (!carried.isEmpty()&&carried.hasTag()) { 수정필요
			if (!carried.isEmpty()) {
				//System.out.println(carried.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL));
				if(carried.getHoverName().getString().equals("토템 발동")){
					LOGGER.info("토템 발동 버튼 눌림");
					client.updateTotemtime();
					ApiRequestManager.uploadTotemData();
				}
				else if(carried.getHoverName().getString().contains("배달 주문") && carried.getHoverName().getString().contains("시작하지 않음") && this.client.data.toggleDeliveryHelper){
					client.deliveryQuests.addLastSelectedQuest(carried.getTooltipLines(Item.TooltipContext.EMPTY, mc.player, TooltipFlag.NORMAL));
				}
				else if(carried.getHoverName().getString().contains("물고기 배달") && carried.getItem().toString().contains("minecraft:knowledge_book")){
					client.deliveryQuests.resetQuestData();
				}
			}
		}
	}
}