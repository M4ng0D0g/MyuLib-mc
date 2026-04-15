package com.myudog.myulib.api.projection.network;

import com.myudog.myulib.Myulib;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public final class ProjectionNetworking {
    public static final Identifier CHANNEL = Identifier.fromNamespaceAndPath(Myulib.MOD_ID, "projection_sync");

    public record ProjectionEntry(String id, AABB bounds, byte flags) {
    }

    public record ProjectionPayload(List<ProjectionEntry> entries) implements CustomPacketPayload {
        public static final Type<ProjectionPayload> TYPE = new Type<>(CHANNEL);
        public static final StreamCodec<RegistryFriendlyByteBuf, ProjectionPayload> CODEC =
                StreamCodec.of(ProjectionPayload::encode, ProjectionPayload::decode);

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        private static ProjectionPayload decode(RegistryFriendlyByteBuf buf) {
            int size = buf.readVarInt();
            List<ProjectionEntry> entries = new ArrayList<>(Math.max(0, size));
            for (int i = 0; i < size; i++) {
                String id = buf.readUtf(256);
                AABB bounds = new AABB(
                        buf.readDouble(),
                        buf.readDouble(),
                        buf.readDouble(),
                        buf.readDouble(),
                        buf.readDouble(),
                        buf.readDouble()
                );
                byte flags = buf.readByte();
                entries.add(new ProjectionEntry(id, bounds, flags));
            }
            return new ProjectionPayload(List.copyOf(entries));
        }

        private static void encode(RegistryFriendlyByteBuf buf, ProjectionPayload payload) {
            List<ProjectionEntry> entries = payload.entries == null ? List.of() : payload.entries;
            buf.writeVarInt(entries.size());
            for (ProjectionEntry entry : entries) {
                buf.writeUtf(entry.id == null ? "" : entry.id, 256);
                buf.writeDouble(entry.bounds.minX);
                buf.writeDouble(entry.bounds.minY);
                buf.writeDouble(entry.bounds.minZ);
                buf.writeDouble(entry.bounds.maxX);
                buf.writeDouble(entry.bounds.maxY);
                buf.writeDouble(entry.bounds.maxZ);
                buf.writeByte(entry.flags);
            }
        }
    }

    private ProjectionNetworking() {
    }

    public static void registerPayloads() {
        PayloadTypeRegistry.clientboundPlay().register(ProjectionPayload.TYPE, ProjectionPayload.CODEC);
    }

    public static void syncToPlayer(ServerPlayer player, List<ProjectionEntry> entries) {
        if (player == null) {
            return;
        }
        ServerPlayNetworking.send(player, new ProjectionPayload(entries == null ? List.of() : List.copyOf(entries)));
    }
}

