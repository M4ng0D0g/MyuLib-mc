package com.myudog.myulib.api.team;

import com.myudog.myulib.api.ui.ConfigurationUiBridge;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.UnaryOperator;

public final class TeamAdminService {
    private TeamAdminService() {
    }

    public static TeamDefinition create(TeamDefinition team) {
        return TeamManager.register(team);
    }

    public static TeamDefinition create(Identifier gameId, TeamDefinition team) {
        return TeamManager.register(gameId, team);
    }

    public static TeamDefinition delete(Identifier teamId) {
        return TeamManager.unregister(teamId);
    }

    public static List<TeamDefinition> deleteGameTeams(Identifier gameId) {
        return TeamManager.unregisterGame(gameId);
    }

    public static TeamDefinition update(Identifier teamId, UnaryOperator<TeamDefinition> updater) {
        return TeamManager.update(teamId, updater);
    }

    public static boolean addPlayer(Identifier teamId, UUID playerId) {
        return TeamManager.addPlayer(teamId, playerId);
    }

    public static boolean removePlayer(UUID playerId) {
        return TeamManager.removePlayer(playerId);
    }

    public static Identifier teamOf(UUID playerId) {
        return TeamManager.teamOf(playerId);
    }

    public static Set<UUID> members(Identifier teamId) {
        return TeamManager.members(teamId);
    }

    public static void forEachMember(Identifier teamId, java.util.function.Consumer<UUID> action) {
        TeamManager.forEachMember(teamId, action);
    }

    public static List<TeamDefinition> list() {
        return TeamManager.all();
    }

    public static List<TeamDefinition> list(Identifier gameId) {
        return TeamManager.all(gameId);
    }

    public static Map<Identifier, TeamDefinition> snapshot() {
        return TeamManager.snapshot();
    }

    public static Map<Identifier, TeamDefinition> snapshot(Identifier gameId) {
        return TeamManager.snapshot(gameId);
    }

    public static void openEditor(Identifier teamId, ConfigurationUiBridge ui) {
        if (ui != null) {
            ui.openTeamEditor(teamId);
        }
    }
}

