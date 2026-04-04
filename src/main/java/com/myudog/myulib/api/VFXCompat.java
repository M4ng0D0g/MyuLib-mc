package com.myudog.myulib.api;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
public final class VFXCompat {
    private VFXCompat() {
    }
    public static void spawnParticles(ServerWorld world, ParticleEffect effect, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
        Method[] methods = ServerWorld.class.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.getName().equals("spawnParticles")) {
                continue;
            }
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 9 && matches(types, 0, ParticleEffect.class) && matches(types, 1, double.class) && matches(types, 2, double.class) && matches(types, 3, double.class)) {
                try {
                    method.setAccessible(true);
                    if (matches(types, 4, int.class)) {
                        method.invoke(world, effect, x, y, z, count, offsetX, offsetY, offsetZ, speed);
                        return;
                    }
                    if (matches(types, 8, int.class)) {
                        method.invoke(world, effect, x, y, z, offsetX, offsetY, offsetZ, speed, count);
                        return;
                    }
                } catch (Throwable ignored) {
                    // try next candidate
                }
            }
            if (types.length == 8 && matches(types, 0, ParticleEffect.class)) {
                try {
                    method.setAccessible(true);
                    for (int i = 0; i < count; i++) {
                        method.invoke(world, effect, x, y, z, offsetX, offsetY, offsetZ, speed);
                    }
                    return;
                } catch (Throwable ignored) {
                    // try next candidate
                }
            }
        }
        throw new NoSuchMethodError("No suitable spawnParticles overload found for this environment");
    }
    private static boolean matches(Class<?>[] types, int index, Class<?> expected) {
        return index >= 0 && index < types.length && types[index].equals(expected);
    }
}