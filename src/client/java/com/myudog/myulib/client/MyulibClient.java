package com.myudog.myulib.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MyulibClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("myulib");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Myulib client bootstrap initialized.");
    }
}

