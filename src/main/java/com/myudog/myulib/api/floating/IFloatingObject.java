package com.myudog.myulib.api.floating;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public interface IFloatingObject {
	void spawn(Vec3d pos);

	void remove();

	void moveTo(Vec3d pos, int interpolationDuration);

	void setScale(Vector3f scale, int interpolationDuration);

	void setRotation(Vector3f leftRotation, int interpolationDuration);
}

