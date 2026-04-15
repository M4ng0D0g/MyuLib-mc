package com.myudog.myulib.api.permission;

import com.myudog.myulib.api.permission.storage.NbtPermissionStorage;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class NbtPermissionStorageRoundTripTest {

    @TempDir
    Path tempDir;

    @Test
    void permissionsPersistAcrossStorageReload() {
        NbtPermissionStorage storage = new NbtPermissionStorage();
        Identifier dimensionId = Identifier.fromNamespaceAndPath("minecraft", "overworld");
        Identifier fieldId = Identifier.fromNamespaceAndPath("tests", "spawn");
        UUID playerId = UUID.fromString("00000000-0000-0000-0000-000000000421");

        storage.bindRoot(tempDir);
        PermissionScope global = new PermissionScope();
        global.forGroup("builders").set(PermissionAction.BLOCK_PLACE, PermissionDecision.ALLOW);
        global.forPlayer(playerId).set(PermissionAction.BLOCK_BREAK, PermissionDecision.DENY);

        PermissionScope dimension = new PermissionScope();
        dimension.forGroup("builders").set(PermissionAction.BLOCK_BREAK, PermissionDecision.ALLOW);

        PermissionScope field = new PermissionScope();
        field.forPlayer(playerId).set(PermissionAction.BLOCK_PLACE, PermissionDecision.DENY);

        storage.save("global", global);
        storage.save("dim:" + dimensionId, dimension);
        storage.save("field:" + fieldId, field);

        Path file = tempDir.resolve("myulib").resolve("permissions.dat");
        assertTrue(Files.exists(file), "Permission storage should write permissions.dat");

        NbtPermissionStorage reloaded = new NbtPermissionStorage();
        reloaded.bindRoot(tempDir);
        Map<String, PermissionScope> loaded = reloaded.loadAll();

        PermissionScope loadedGlobal = loaded.get("global");
        PermissionScope loadedDimension = loaded.get("dim:" + dimensionId);
        PermissionScope loadedField = loaded.get("field:" + fieldId);

        assertEquals(PermissionDecision.ALLOW,
                loadedGlobal.forGroup("builders").get(PermissionAction.BLOCK_PLACE));
        assertEquals(PermissionDecision.DENY,
                loadedGlobal.forPlayer(playerId).get(PermissionAction.BLOCK_BREAK));
        assertEquals(PermissionDecision.ALLOW,
                loadedDimension.forGroup("builders").get(PermissionAction.BLOCK_BREAK));
        assertEquals(PermissionDecision.DENY,
                loadedField.forPlayer(playerId).get(PermissionAction.BLOCK_PLACE));
    }
}

