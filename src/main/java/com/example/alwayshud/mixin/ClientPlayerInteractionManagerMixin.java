package com.example.alwayshud.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 원래 hasStatusBars()는 GameMode.SURVIVAL / GameMode.ADVENTURE 일 때만 true를 반환해서
 * 크리에이티브 모드에서 체력바, 배고픔바, 갑옷, 산소바 등이 렌더링되지 않습니다.
 * (경험치/인챈트 바는 hasExperienceBar()가 담당하며 스펙테이터가 아니면 원래도 표시됩니다.)
 *
 * 이 Mixin은 스펙테이터 모드가 아닌 이상 항상 true를 반환하도록 강제해서
 * 크리에이티브 모드에서도 서바이벌 HUD가 그대로 보이게 만듭니다.
 */
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

	@Inject(method = "hasStatusBars", at = @At("HEAD"), cancellable = true)
	private void alwayshud$forceStatusBars(CallbackInfoReturnable<Boolean> cir) {
		ClientPlayerInteractionManager self = (ClientPlayerInteractionManager) (Object) this;
		GameMode mode = self.getCurrentGameMode();

		// 스펙테이터 모드에서는 원래 동작(숨김)을 유지하고,
		// 그 외 모드(크리에이티브 포함)에서는 항상 HUD를 표시합니다.
		if (mode != GameMode.SPECTATOR) {
			cir.setReturnValue(true);
		}
	}
}
