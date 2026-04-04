package com.myudog.myulib.internal.dynamics;

import com.myudog.myulib.api.dynamics.IForceField;
import net.minecraft.util.math.Vec3d;

public final class RadialForceField implements IForceField {
    private final boolean attractive;
    private final double maxRange;

    public RadialForceField(boolean attractive) {
        this(attractive, 10.0);
    }

    public RadialForceField(boolean attractive, double maxRange) {
        this.attractive = attractive;
        this.maxRange = Math.max(0.0001, maxRange);
    }

    @Override
    public Vec3d calculateForce(Vec3d pos, Vec3d center, double strength) {
        Vec3d direction = attractive ? center.subtract(pos) : pos.subtract(center);
        double distanceSq = direction.lengthSquared();
        if (distanceSq < 0.0001 || distanceSq > maxRange * maxRange) {
            return Vec3d.ZERO;
        }

        double distance = Math.sqrt(distanceSq);
        double falloff = (maxRange - distance) / maxRange;
        double magnitude = strength * falloff;
        return direction.normalize().multiply(magnitude);
    }
}
