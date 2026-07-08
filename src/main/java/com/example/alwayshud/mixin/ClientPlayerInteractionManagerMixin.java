package com.example.alwayshud.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

	@Inject(method = "hasStatusBars", at = @At("HEAD"), cancellable = true)
	private void alwayshud$forceStatusBars(CallbackInfoReturnable<Boolean> cir) {
		ClientPlayerInteractionManager self = (ClientPlayerInteractionManager) (Object) this;
		GameMode mode = self.getCurrentGameMode();

		if (mode != GameMode.SPECTATOR) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "hasExperienceBar", at = @At("HEAD"), cancellable = true)
	private void alwayshud$forceExperienceBar(CallbackInfoReturnable<Boolean> cir) {
		ClientPlayerInteractionManager self = (ClientPlayerInteractionManager) (Object) this;
		GameMode mode = self.getCurrentGameMode();

		if (mode != GameMode.SPECTATOR) {
			cir.setReturnValue(true);
		}
	}
}
