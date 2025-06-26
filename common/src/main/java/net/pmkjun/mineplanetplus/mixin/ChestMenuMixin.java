package net.pmkjun.mineplanetplus.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.pmkjun.mineplanetplus.fishhelper.FishHelperClient;
import net.pmkjun.mineplanetplus.serverutility.ServerUtilityClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestMenu.class)
public abstract class ChestMenuMixin extends AbstractContainerMenu {
    private final FishHelperClient client = FishHelperClient.getInstance();
    private final Minecraft mc = Minecraft.getInstance();
    private final ServerUtilityClient serverutility = ServerUtilityClient.getInstance();

    protected ChestMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method = "quickMoveStack", at = @At("HEAD"))
    private void quickMoveStack(Player player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if(player instanceof LocalPlayer){
            Slot slot = this.slots.get(index);
            mc.player.displayClientMessage(Component.literal("아이템 퀵 무브 감지 : "+slot.getItem().getHoverName().getString()),false);
        }
    }
}