package net.pmkjun.mineplanetplus.mixin;

import java.io.File;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.resources.DownloadedPackSource;
import net.minecraft.server.packs.repository.PackSource;
import net.pmkjun.mineplanetplus.resourcepackpreloader.ServerUnpacker;

@Mixin(DownloadedPackSource.class)
public class DownloadedPackSourceMixin {
    @Inject(method = "setServerPack", at = @At("TAIL"))
    public void loadServerPack(File file, PackSource packSource, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
		  ServerUnpacker.extractServerPack(file);
    }
}