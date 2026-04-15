package com.myudog.myulib.mixin;

import com.myudog.myulib.api.permission.PermissionAction;
import com.myudog.myulib.api.permission.PermissionGate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayerEntity {

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(Entity target, CallbackInfo ci) {
        Player attacker = (Player) (Object) this;

        PermissionAction action = PermissionAction.ATTACK_HOSTILE_MOB;
        if (target instanceof Player) action = PermissionAction.ATTACK_PLAYER;
        else if (target instanceof Animal || target instanceof Villager) action = PermissionAction.ATTACK_FRIENDLY_MOB;

        if (PermissionGate.isDenied(attacker, action, target.position())) {
            ci.cancel();
        }
    }
}
