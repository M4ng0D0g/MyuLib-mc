package com.myudog.myulib.api.dsl;
import com.myudog.myulib.api.Shapes;
import com.myudog.myulib.api.color.ColorProvider;
import com.myudog.myulib.api.dynamics.IForceField;
import com.myudog.myulib.api.floating.IFloatingObject;
import com.myudog.myulib.api.shape.IShape;
import com.myudog.myulib.internal.monitor.VFXMonitor;
import com.myudog.myulib.internal.scheduler.EffectTicker;
import com.myudog.myulib.internal.state.ParticleState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
public final class EffectBuilder {
    @FunctionalInterface
    public interface ParticleRenderCallback {
        void render(Vec3d pos, Vector3f color, float progress);
    }
    public static final class ForceFieldBinding {
        public final IForceField field;
        public final double strength;
        public ForceFieldBinding(IForceField field, double strength) {
            this.field = field;
            this.strength = strength;
        }
    }
    private int duration = 20;
    private final List<IntConsumer> tickActions = new ArrayList<>();
    private Supplier<Vec3d> centerProvider;
    public EffectBuilder(Vec3d center) {
        this.centerProvider = () -> center;
    }
    public void duration(int ticks) {
        this.duration = Math.max(0, ticks);
    }
    public void follow(Entity entity, Vec3d offset) {
        this.centerProvider = () -> new Vec3d(entity.getX(), entity.getY(), entity.getZ()).add(offset);
    }
    public void follow(Supplier<Vec3d> provider) {
        this.centerProvider = provider;
    }
    private Vec3d getCurrentCenter() {
        return centerProvider.get();
    }
    public void onTick(IntConsumer action) {
        tickActions.add(action);
    }
    public void bindObject(IFloatingObject obj, IntFunction<Vec3d> path) {
        onTick(tick -> obj.moveTo(path.apply(tick), 1));
    }
    public void renderShape(
            IShape shape,
            boolean isSolid,
            Vec3d size,
            double density,
            String colorStart,
            String colorEnd,
            int rainbowPeriod,
            List<ForceFieldBinding> forceFields,
            double friction,
            boolean useCollision,
            double bounce,
            int minLife,
            int maxLife,
            ParticleRenderCallback onRender
    ) {
        List<Vec3d> points = isSolid ? shape.getSolidPoints(size, density) : shape.getOutlinePoints(size, density);
        List<ParticleState> particles = new ArrayList<>(points.size());
        for (Vec3d point : points) {
            int life = minLife + (int) (Math.random() * Math.max(1, maxLife - minLife + 1));
            particles.add(new ParticleState(point, new Vec3d(0.0, 0.0, 0.0), 0, life));
        }
        Vector3f startRGB = ColorProvider.hexToRGB(colorStart);
        Vector3f endRGB = colorEnd == null ? null : ColorProvider.hexToRGB(colorEnd);
        List<ForceFieldBinding> safeForceFields = forceFields == null ? List.of() : forceFields;
        onTick(tick -> {
            if (!VFXMonitor.requestSpawn(particles.size())) {
                return;
            }
            Vec3d currentCenter = getCurrentCenter();
            Iterator<ParticleState> iterator = particles.iterator();
            while (iterator.hasNext()) {
                ParticleState particle = iterator.next();
                particle.age++;
                if (particle.isDead()) {
                    iterator.remove();
                    continue;
                }
                Vec3d totalForce = new Vec3d(0.0, 0.0, 0.0);
                for (ForceFieldBinding binding : safeForceFields) {
                    totalForce = totalForce.add(binding.field.calculateForce(particle.pos, currentCenter, binding.strength));
                }
                particle.vel = particle.vel.add(totalForce).multiply(friction);
                if (useCollision) {
                    double damp = Math.max(0.0, 1.0 - Math.max(0.0, bounce));
                    particle.vel = particle.vel.multiply(damp);
                }
                particle.pos = particle.pos.add(particle.vel);
                Vector3f currentColor;
                if (rainbowPeriod > 0) {
                    currentColor = ColorProvider.getRainbowColor(tick + (int) (particle.randomSeed * 100.0), rainbowPeriod);
                } else if (endRGB != null) {
                    currentColor = ColorProvider.lerp(startRGB, endRGB, particle.getProgress());
                } else {
                    currentColor = startRGB;
                }
                onRender.render(currentCenter.add(particle.pos), currentColor, particle.getProgress());
            }
        });
    }
    public void forCircle(double radius, double density, Consumer<Vec3d> action) {
        onTick(tick -> {
            Vec3d centerPos = getCurrentCenter();
            for (Vec3d offset : Shapes.CIRCLE.getOutlinePoints(new Vec3d(radius, 0.0, radius), density)) {
                action.accept(centerPos.add(offset));
            }
        });
    }
    public void build() {
        final int[] current = {0};
        EffectTicker.addTask(() -> {
            if (current[0] >= duration) {
                return false;
            }
            for (IntConsumer action : tickActions) {
                action.accept(current[0]);
            }
            current[0]++;
            return true;
        });
    }
    public static void spawnEffect(Vec3d center, Consumer<EffectBuilder> setup) {
        EffectBuilder builder = new EffectBuilder(center);
        setup.accept(builder);
        builder.build();
    }
}