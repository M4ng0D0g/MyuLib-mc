package com.myudog.myulib.api.dynamics;
import net.minecraft.util.math.Vec3d;
public interface IForceField {
    Vec3d calculateForce(Vec3d pos, Vec3d center, double strength);
}