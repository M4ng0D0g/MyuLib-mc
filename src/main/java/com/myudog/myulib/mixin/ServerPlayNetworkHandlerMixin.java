package com.myudog.myulib.mixin;

import com.myudog.myulib.api.game.GameManager;
import com.myudog.myulib.api.game.instance.GameInstance;
import com.myudog.myulib.api.game.object.GameObjectKind;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow public net.minecraft.server.network.ServerPlayerEntity player;
    @Shadow public net.minecraft.world.World world;

    @Inject(method = "onPlayerInteractBlock", at = @At("HEAD"), cancellable = true)
    private void myulib$onPlayerInteractBlock(PlayerInteractBlockC2SPacket packet, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) this.world;
        BlockPos pos = packet.getBlockHitResult().getBlockPos();
        BlockState state = world.getBlockState(pos);
        Identifier blockId = Registries.BLOCK.getId(state.getBlock());
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("pos", pos.toShortString());
        payload.put("hand", packet.getHand().name());
        payload.put("action", "use_block");
        Identifier sourceId = playerId();
        boolean consumed = dispatch(blockId, sourceId, payload,
            GameObjectKind.RESPAWN_POINT,
            GameObjectKind.USABLE,
            GameObjectKind.INTERACTABLE);
        if (consumed) {
            ci.cancel();
        }
    }

    @Inject(method = "onPlayerAction", at = @At("HEAD"), cancellable = true)
    private void myulib$onPlayerAction(PlayerActionC2SPacket packet, CallbackInfo ci) {
        if (packet.getAction() != PlayerActionC2SPacket.Action.START_DESTROY_BLOCK && packet.getAction() != PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK) {
            return;
        }
        ServerWorld world = (ServerWorld) this.world;
        BlockPos pos = packet.getPos();
        BlockState state = world.getBlockState(pos);
        Identifier blockId = Registries.BLOCK.getId(state.getBlock());
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("pos", pos.toShortString());
        payload.put("action", packet.getAction().name());
        Identifier sourceId = playerId();
        boolean consumed = dispatch(blockId, sourceId, payload,
            GameObjectKind.MINEABLE,
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

    private Identifier playerId() {
        return Identifier.of("myulib", "player_" + player.getUuidAsString().replace("-", ""));
    }
}




