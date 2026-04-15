package com.myudog.myulib.mixin;

import com.myudog.myulib.api.permission.PermissionAction;
import com.myudog.myulib.api.permission.PermissionGate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayerInteractEntity {

    @Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
    private void onInteractEntity(Entity entity, InteractionHand hand, Vec3 location, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player) (Object) this;

        PermissionAction action = PermissionAction.INTERACT_ENTITY;
        if (entity instanceof ArmorStand) action = PermissionAction.ARMOR_STAND_MANIPULATE;
        else if (entity instanceof PlayerRideableJumping || entity instanceof VehicleEntity || entity instanceof HappyGhast) {
            action = PermissionAction.RIDE_ENTITY;
        }

        if (PermissionGate.isDenied(player, action, entity.position())) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}