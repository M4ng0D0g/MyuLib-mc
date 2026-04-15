package com.myudog.myulib.api.rolegroup;

import com.myudog.myulib.Myulib;
import com.myudog.myulib.api.rolegroup.storage.NbtRoleGroupStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class NbtRoleGroupLegacyIdReadTest {

    @TempDir
    Path tempDir;

    @Test
    void legacyNonNamespacedIdsAreLoadedAsMyulibIdentifiers() throws Exception {
        UUID playerId = UUID.fromString("00000000-0000-0000-0000-000000000422");
        Path storageFile = tempDir.resolve("myulib").resolve("rolegroups.dat");

        writeLegacyRoleGroupFile(storageFile, playerId);

        NbtRoleGroupStorage storage = new NbtRoleGroupStorage();
        storage.bindRoot(tempDir);

        Identifier buildersId = Identifier.fromNamespaceAndPath(Myulib.MOD_ID, "builders");
        RoleGroupDefinition group = storage.loadGroups().get(buildersId);

        assertNotNull(group, "Legacy id should be loaded as a valid RoleGroupDefinition");
        assertEquals(buildersId, group.id(), "Legacy id should be normalized to myulib namespace");
        assertTrue(storage.loadAssignments().getOrDefault(playerId, java.util.Set.of()).contains(buildersId),
                "Membership should resolve to normalized Identifier");
    }

    private static void writeLegacyRoleGroupFile(Path storageFile, UUID playerId) throws Exception {
        Files.createDirectories(storageFile.getParent());

        CompoundTag root = new CompoundTag();

        ListTag groups = new ListTag();
        CompoundTag group = new CompoundTag();
        group.putString("id", "builders");
        group.putString("displayName", "Builders");
        group.putInt("priority", 10);
        group.put("metadata", new CompoundTag());
        group.put("members", new CompoundTag());
        groups.add(group);
        root.put("groups", groups);

        CompoundTag memberships = new CompoundTag();
        ListTag playerMemberships = new ListTag();
        playerMemberships.add(StringTag.valueOf("builders"));
        memberships.put(playerId.toString(), playerMemberships);
        root.put("memberships", memberships);

        writeRoot(storageFile, root);
    }

    private static void writeRoot(Path path, CompoundTag root) throws Exception {
        Method method = findNbtIoWriteMethod("writeCompressed");
        if (method == null) {
            method = findNbtIoWriteMethod("write");
        }
        if (method == null) {
            throw new NoSuchMethodException("No suitable NbtIo write method found");
        }

        Class<?>[] params = method.getParameterTypes();
        if (params.length == 2 && CompoundTag.class.isAssignableFrom(params[0]) && Path.class.isAssignableFrom(params[1])) {
            method.invoke(null, root, path);
            return;
        }
        if (params.length == 1 && Path.class.isAssignableFrom(params[0])) {
            method.invoke(null, path);
            return;
        }
        if (params.length == 2 && CompoundTag.class.isAssignableFrom(params[0])
                && java.io.OutputStream.class.isAssignableFrom(params[1])) {
            try (java.io.OutputStream outputStream = Files.newOutputStream(path)) {
                method.invoke(null, root, outputStream);
            }
            return;
        }
        throw new NoSuchMethodException("Unsupported NbtIo write method signature");
    }

    private static Method findNbtIoWriteMethod(String name) {
        for (Method method : NbtIo.class.getMethods()) {
            if (!method.getName().equals(name)) {
                continue;
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 2 && CompoundTag.class.isAssignableFrom(params[0])
                    && (Path.class.isAssignableFrom(params[1]) || java.io.OutputStream.class.isAssignableFrom(params[1]))) {
                return method;
            }
        }
        return null;
    }
}

