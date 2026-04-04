package com.myudog.myulib.api;

import com.myudog.myulib.api.dsl.EffectBuilder;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public final class MyuVFXManager {
    private MyuVFXManager() {
    }

    public static void spawnSpiral(ServerWorld world, Vec3d center, ParticleEffect particle) {
        EffectBuilder.spawnEffect(center, builder -> {
            builder.duration(40);
            builder.onTick(tick -> {
                double angle = tick * 0.5;
                double radius = 1.5;
                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);
                double y = tick * 0.1;
                VFXCompat.spawnParticles(world, particle, center.x + x, center.y + y, center.z + z, 1, 0.0, 0.0, 0.0, 0.0);
            });
        });
    }

    public static void spawnShockwave(ServerWorld world, Vec3d center, ParticleEffect particle) {
        EffectBuilder.spawnEffect(center, builder -> {
            builder.duration(15);
            builder.onTick(tick -> {
                double radius = tick * 0.8;
                double density = Math.max(8.0, radius * 10.0);
                for (Vec3d pos : Shapes.CIRCLE.getOutlinePoints(new Vec3d(radius, 0.0, radius), density)) {
                    VFXCompat.spawnParticles(world, particle, center.x + pos.x, center.y + pos.y, center.z + pos.z, 1, 0.0, 0.1, 0.0, 0.02);
                }
            });
        });
    }
}