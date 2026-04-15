package com.myudog.myulib.api.ui;

import com.myudog.myulib.api.permission.ScopeLayer;
import net.minecraft.resources.Identifier;

public interface ConfigurationUiBridge {
    void openFieldEditor(Identifier fieldId);

    void openIdentityGroupEditor(Identifier groupId);

    void openRoleGroupEditor(Identifier groupId);

    void openTeamEditor(Identifier teamId);

    void openPermissionEditor(ScopeLayer layer, String scopeId);
}

