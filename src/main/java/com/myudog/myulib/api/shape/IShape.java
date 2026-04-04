package com.myudog.myulib.api.shape;
import net.minecraft.util.math.Vec3d;
import java.util.List;
public interface IShape {
    List<Vec3d> getOutlinePoints(Vec3d size, double density);
    List<Vec3d> getSolidPoints(Vec3d size, double density);
}