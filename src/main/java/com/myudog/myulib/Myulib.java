package com.myudog.myulib;

import com.myudog.myulib.api.MyulibApi;
import com.myudog.myulib.api.field.FieldManager;
import com.myudog.myulib.api.permission.PermissionManager;
import com.myudog.myulib.api.rolegroup.RoleGroupManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Myulib implements ModInitializer {
    public static final String MOD_ID = "myulib";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Mango UI is initializing...");

        MyulibApi.init();

        LOGGER.info("MyuLib (by MyuDog) has been initialized successfully.");
    }
}


