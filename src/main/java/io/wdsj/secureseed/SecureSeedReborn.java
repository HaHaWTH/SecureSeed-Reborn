package io.wdsj.secureseed;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureSeedReborn implements ModInitializer {
	public static final String MOD_ID = "secure-seed-reborn";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Secure Seed Reborn Initialized");
	}
}