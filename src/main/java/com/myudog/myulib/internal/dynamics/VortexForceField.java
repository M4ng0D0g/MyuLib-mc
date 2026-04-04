package com.myudog.myulib.internal.dynamics;

import com.myudog.myulib.api.dynamics.IForceField;
import net.minecraft.util.math.Vec3d;

public final class VortexForceField implements IForceField {
    private final Vec3d axis;

    public VortexForceField() {
        this(new Vec3d(0.0, 1.0, 0.0));
    }

    public VortexForceField(Vec3d axis) {
        Vec3d normalized = axis == null ? new Vec3d(0.0, 1.0, 0.0) : axis;
        double lengthSq = normalized.lengthSquared();
        this.axis = lengthSq < 0.0001 ? new Vec3d(0.0, 1.0, 0.0) : normalized.normalize();
    }

    @Override
    public Vec3d calculateForce(Vec3d pos, Vec3d center, double strength) {
        Vec3d relativePos = pos.subtract(center);
        if (relativePos.lengthSquared() < 0.0001) {
            return Vec3d.ZERO;
        }

        Vec3d tangent = relativePos.crossProduct(axis);
        if (tangent.lengthSquared() < 0.0001) {
            return Vec3d.ZERO;
        }

        return tangent.normalize().multiply(strength);
    }
}
