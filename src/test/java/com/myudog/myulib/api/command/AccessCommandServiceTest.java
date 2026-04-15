package com.myudog.myulib.api.command;
import com.myudog.myulib.api.field.FieldDefinition;
import com.myudog.myulib.api.field.FieldManager;
import com.myudog.myulib.api.permission.PermissionAction;
import com.myudog.myulib.api.permission.PermissionDecision;
import com.myudog.myulib.api.permission.PermissionManager;
import com.myudog.myulib.api.rolegroup.RoleGroupManager;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
final class AccessCommandServiceTest {
    @TempDir
    Path tempDir;
    @Test
    void accessServiceHelpersCreateAndPersistAccessData() {
        RoleGroupManager.clear();
        PermissionManager.clear();
        FieldManager.clear();
        CommandRegistry.clear();
        assertDoesNotThrow(AccessCommandService::registerDefaults,
                "registerDefaults should only attach the command callback");
        assertTrue(CommandRegistry.snapshot().containsKey("myulib:save"),
                "registerDefaults should register the /myulib: local command mirror");
        Identifier builderId = Identifier.fromNamespaceAndPath("myulib", "builders");
        AccessCommandService.createRoleGroup(builderId, Component.literal("Builders"), 7);
        assertEquals("Builders", RoleGroupManager.get(builderId).translationKey().getString(),
                "createRoleGroup should register the new group");
        AccessCommandService.grantGlobalPermission("builders", PermissionAction.BLOCK_PLACE, PermissionDecision.ALLOW);
        assertEquals(PermissionDecision.ALLOW,
                PermissionManager.global().forGroup("builders").get(PermissionAction.BLOCK_PLACE),
                "grantGlobalPermission should update the global permission table");
        Identifier fieldId = Identifier.fromNamespaceAndPath("tests", "spawn");
        FieldDefinition field = new FieldDefinition(
                fieldId,
                Identifier.fromNamespaceAndPath("minecraft", "overworld"),
                new AABB(0, 0, 0, 10, 10, 10),
                Map.of("label", "Spawn")
        );
        AccessCommandService.createField(field);
        assertEquals(field, FieldManager.get(fieldId), "createField should register the field");
        assertTrue(AccessCommandService.listRoleGroups().stream().anyMatch(group -> group.id().equals(builderId)),
                "listRoleGroups should include the created group");
        AccessCommandService.deleteField(fieldId);
        AccessCommandService.deleteRoleGroup(builderId);
        assertNull(FieldManager.get(fieldId), "deleteField should remove the field");
        assertNull(RoleGroupManager.get(builderId), "deleteRoleGroup should remove the role group");

        CommandResult save = CommandRegistry.execute(new CommandContext("console", "myulib:save", Map.of()));
        assertTrue(save.success(), "The mirrored myulib:save command should execute successfully");
        CommandResult status = CommandRegistry.execute(new CommandContext("console", "myulib:status", Map.of()));
        assertTrue(status.success(), "The mirrored myulib:status command should execute successfully");
        assertTrue(status.message().contains("field="), "Status output should include field count");
    }
}
