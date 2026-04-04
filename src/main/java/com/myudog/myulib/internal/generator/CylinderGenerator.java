package com.myudog.myulib.internal.generator;
import com.myudog.myulib.api.Shapes;
import com.myudog.myulib.api.shape.IShape;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.List;
public final class CylinderGenerator implements IShape {
    @Override
    public List<Vec3d> getOutlinePoints(Vec3d size, double density) {
        List<Vec3d> points = new ArrayList<>();
        double radiusX = size.x;
        double radiusZ = size.z;
        double height = size.y;
        int circlePoints = Math.max(8, (int) Math.round((radiusX + radiusZ) * Math.PI * Math.max(1.0, density)));
        for (int i = 0; i < circlePoints; i++) {
            double angle = 2.0 * Math.PI * i / circlePoints;
            double x = radiusX * Math.cos(angle);
            double z = radiusZ * Math.sin(angle);
            points.add(new Vec3d(x, 0.0, z));
            points.add(new Vec3d(x, height, z));
        }
        int verticalSteps = Math.max(1, (int) Math.round(height * Math.max(1.0, density)));
        for (int i = 0; i < verticalSteps; i++) {
            double h = i / Math.max(1.0, density);
            for (int j = 0; j < 4; j++) {
                double angle = j * Math.PI / 2.0;
                points.add(new Vec3d(radiusX * Math.cos(angle), h, radiusZ * Math.sin(angle)));
            }
        }
        return points;
    }
    @Override
    public List<Vec3d> getSolidPoints(Vec3d size, double density) {
        List<Vec3d> points = new ArrayList<>();
        double hStep = 1.0 / Math.max(0.1, density);
        for (double h = 0.0; h <= size.y; h += hStep) {
            for (Vec3d point : Shapes.CIRCLE.getSolidPoints(new Vec3d(size.x, 0.0, size.z), density)) {
                points.add(new Vec3d(point.x, h, point.z));
            }
        }
        return points;
    }
}