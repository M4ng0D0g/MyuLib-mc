package com.myudog.myulib.internal.entity;

import com.myudog.myulib.api.floating.IFloatingObject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity.ItemDisplayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class ItemDisplayObject implements IFloatingObject {
	private final ServerWorld world;
	private final ItemStack itemStack;
	private ItemDisplayEntity entity;
	private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
	private Vector3f rotation = new Vector3f();

	public ItemDisplayObject(ServerWorld world, ItemStack itemStack) {
		this.world = world;
		this.itemStack = itemStack;
	}

	@Override
	public void spawn(Vec3d pos) {
		ItemDisplayEntity display = new ItemDisplayEntity(EntityType.ITEM_DISPLAY, world);
		display.setItemStack(itemStack.copy());
		display.setPosition(pos.x, pos.y, pos.z);
		world.spawnEntity(display);
		entity = display;
	}

	@Override
	public void remove() {
		if (entity != null) {
			entity.discard();
			entity = null;
		}
	}

	@Override
	public void moveTo(Vec3d pos, int interpolationDuration) {
		if (entity != null) {
			entity.setPosition(pos.x, pos.y, pos.z);
		}
	}

	@Override
	public void setScale(Vector3f scale, int interpolationDuration) {
		this.scale = new Vector3f(scale);
	}

	@Override
	public void setRotation(Vector3f leftRotation, int interpolationDuration) {
		this.rotation = new Vector3f(leftRotation);
	}
}
