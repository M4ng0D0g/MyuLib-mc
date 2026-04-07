package com.myudog.myulib.mixin;

import com.myudog.myulib.api.game.GameManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	@Inject(method = "tick", at = @At("TAIL"))
	private void myulib$tick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
		GameManager.tickAll();
	}
}