package com.myudog.myulib.mixin;

import com.myudog.myulib.api.game.GameManager;
import com.myudog.myulib.api.game.instance.GameInstance;
import com.myudog.myulib.api.game.object.GameObjectKind;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void myulib$attack(Entity target, CallbackInfo ci) {
        ServerPlayerEntity self = (ServerPlayerEntity) (Object) this;
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("entity", String.valueOf(target.getId()));
        payload.put("type", Registries.ENTITY_TYPE.getId(target.getType()).toString());
        payload.put("action", "attack");
        Identifier sourceId = playerId(self);
        boolean consumed = dispatch(Registries.ENTITY_TYPE.getId(target.getType()), sourceId, payload,
            GameObjectKind.ATTACKABLE,
            GameObjectKind.INTERACTABLE);
        if (consumed) {
            ci.cancel();
        }
    }

    private boolean dispatch(Identifier type, Identifier sourceId, Map<String, String> payload, GameObjectKind... kinds) {
        boolean consumed = false;
        for (GameInstance<?> instance : GameManager.getInstances()) {
            for (GameObjectKind kind : kinds) {
                consumed |= instance.objectBindings().interactByKindAndType(instance, kind, type, sourceId, payload);
            }
        }
        return consumed;
    }

    private Identifier playerId(ServerPlayerEntity player) {
        return Identifier.of("myulib", "player_" + player.getUuidAsString().replace("-", ""));
    }
}


