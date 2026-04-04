package com.myudog.myulib.api.dynamics;

import com.myudog.myulib.internal.dynamics.RadialForceField;
import com.myudog.myulib.internal.dynamics.VortexForceField;
import net.minecraft.util.math.Vec3d;

public final class ForceFields {
    public static final IForceField ATTRACTION = new RadialForceField(true);
    public static final IForceField REPULSION = new RadialForceField(false);
    public static final IForceField VORTEX = new VortexForceField();

    private ForceFields() {
    }

    public static IForceField attraction(double range) {
        return new RadialForceField(true, range);
    }

    public static IForceField repulsion(double range) {
        return new RadialForceField(false, range);
    }

    public static IForceField vortex() {
        return new VortexForceField();
    }

    public static IForceField vortex(Vec3d axis) {
        return new VortexForceField(axis);
    }
}
