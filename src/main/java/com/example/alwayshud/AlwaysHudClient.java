package com.example.alwayshud;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlwaysHudClient implements ClientModInitializer {
	public static final String MOD_ID = "alwayshud";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Always Survival HUD 모드가 로드되었습니다. 크리에이티브 모드에서도 체력/배고픔/경험치 바가 표시됩니다.");
	}
}
