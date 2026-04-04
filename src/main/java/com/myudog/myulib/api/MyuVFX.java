package com.myudog.myulib.api;

import com.myudog.myulib.api.floating.IFloatingObject;
import com.myudog.myulib.internal.entity.ItemDisplayObject;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public final class MyuVFX {
	private MyuVFX() {
	}

	public static IFloatingObject createItemObject(ServerWorld world, ItemStack itemStack) {
		return new ItemDisplayObject(world, itemStack);
	}
}
