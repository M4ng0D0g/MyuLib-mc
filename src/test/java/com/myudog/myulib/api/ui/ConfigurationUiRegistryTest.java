package com.myudog.myulib.api.ui;
import com.myudog.myulib.api.permission.ScopeLayer;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
final class ConfigurationUiRegistryTest {
    @AfterEach
    void reset() {
        ConfigurationUiRegistry.setBridge(null);
    }
    @Test
    void registryForwardsCallsAndNoopBridgeStaysSilent() {
        RecordingBridge bridge = new RecordingBridge();
        ConfigurationUiRegistry.setBridge(bridge);
        ConfigurationUiRegistry.openFieldEditor(Identifier.fromNamespaceAndPath("tests", "field"));
        ConfigurationUiRegistry.openIdentityGroupEditor(Identifier.fromNamespaceAndPath("tests", "identity"));
        ConfigurationUiRegistry.openRoleGroupEditor(Identifier.fromNamespaceAndPath("tests", "role"));
        ConfigurationUiRegistry.openTeamEditor(Identifier.fromNamespaceAndPath("tests", "team"));
        ConfigurationUiRegistry.openPermissionEditor(ScopeLayer.FIELD, "scope");
        assertEquals(List.of(
                "field:tests:field",
                "identity:tests:identity",
                "role:tests:role",
                "team:tests:team",
                "permission:FIELD:scope"
        ), bridge.calls, "ConfigurationUiRegistry should forward calls in order");
        assertDoesNotThrow(() -> {
            NoopConfigurationUiBridge.INSTANCE.openFieldEditor(Identifier.fromNamespaceAndPath("tests", "ignored_field"));
            NoopConfigurationUiBridge.INSTANCE.openIdentityGroupEditor(Identifier.fromNamespaceAndPath("tests", "ignored_identity"));
            NoopConfigurationUiBridge.INSTANCE.openRoleGroupEditor(Identifier.fromNamespaceAndPath("tests", "ignored_role"));
            NoopConfigurationUiBridge.INSTANCE.openTeamEditor(Identifier.fromNamespaceAndPath("tests", "ignored_team"));
            NoopConfigurationUiBridge.INSTANCE.openPermissionEditor(ScopeLayer.GLOBAL, "ignored");
        }, "NoopConfigurationUiBridge should ignore all calls without throwing");
    }
    private static final class RecordingBridge implements ConfigurationUiBridge {
        private final List<String> calls = new ArrayList<>();
        @Override
        public void openFieldEditor(Identifier fieldId) {
            calls.add("field:" + fieldId);
        }
        @Override
        public void openIdentityGroupEditor(Identifier groupId) {
            calls.add("identity:" + groupId);
        }
        @Override
        public void openRoleGroupEditor(Identifier groupId) {
            calls.add("role:" + groupId);
        }
        @Override
        public void openTeamEditor(Identifier teamId) {
            calls.add("team:" + teamId);
        }
        @Override
        public void openPermissionEditor(ScopeLayer layer, String scopeId) {
            calls.add("permission:" + layer + ":" + scopeId);
        }
    }
}
