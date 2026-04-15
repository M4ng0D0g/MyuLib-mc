package com.myudog.myulib.api.rolegroup;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface RoleGroupStorage {
    void initialize(MinecraftServer server);

    Map<Identifier, RoleGroupDefinition> loadGroups();

    Map<UUID, Set<Identifier>> loadAssignments();

    void saveGroup(RoleGroupDefinition group);

    void deleteGroup(Identifier groupId);

    void saveAssignments(UUID playerId, Set<Identifier> groupIds);
}

