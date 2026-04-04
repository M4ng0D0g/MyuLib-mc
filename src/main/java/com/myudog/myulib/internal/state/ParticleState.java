package com.myudog.myulib.internal.state;
import net.minecraft.util.math.Vec3d;
public final class ParticleState {
    public Vec3d pos;
    public Vec3d vel;
    public int age;
    public final int maxAge;
    public final double randomSeed;
    public ParticleState(Vec3d pos, Vec3d vel, int age, int maxAge) {
        this(pos, vel, age, maxAge, Math.random());
    }
    public ParticleState(Vec3d pos, Vec3d vel, int age, int maxAge, double randomSeed) {
        this.pos = pos;
        this.vel = vel;
        this.age = age;
        this.maxAge = maxAge;
        this.randomSeed = randomSeed;
    }
    public float getProgress() {
        if (maxAge <= 0) {
            return 1.0f;
        }
        return Math.min(1.0f, age / (float) maxAge);
    }
    public boolean isDead() {
        return age >= maxAge;
    }
}