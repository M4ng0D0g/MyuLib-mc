package com.myudog.myulib.api.team;

import com.myudog.myulib.api.debug.DebugFeature;
import com.myudog.myulib.api.debug.DebugLogManager;
import com.myudog.myulib.api.util.ShortIdRegistry;
import com.myudog.myulib.api.storage.DataStorage;
import com.myudog.myulib.api.team.storage.NbtTeamStorage;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public final class TeamManager {
    // 🌟 全面統一使用 Identifier 作為 Key
    private static final Map<Identifier, TeamDefinition> TEAMS = new LinkedHashMap<>();
    private static final Map<Identifier, Set<UUID>> TEAM_MEMBERS = new LinkedHashMap<>();
    private static final Map<UUID, Identifier> PLAYER_TEAM = new LinkedHashMap<>();
    private static final Map<Identifier, Identifier> TEAM_GAME_IDS = new LinkedHashMap<>();
    private static final ShortIdRegistry ID_REGISTRY = new ShortIdRegistry(6);

    // 🌟 儲存庫實例
    private static DataStorage<Identifier, TeamDefinition> storage;

    private TeamManager() {
    }

    public static void install() {
        install(new NbtTeamStorage());
    }

    /**
     * 🌟 依賴注入：在模組啟動時，傳入指定的儲存庫實作
     */
    public static void install(DataStorage<Identifier, TeamDefinition> storageProvider) {
        storage = storageProvider;

        // 掛載伺服器啟動事件，初始化儲存庫並載入所有資料
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            if (storage != null) {
                storage.initialize(server);

                // 將儲存庫中的資料載入記憶體
                Map<Identifier, TeamDefinition> loadedData = storage.loadAll();
                TEAMS.clear();
                ID_REGISTRY.clear();
                if (loadedData != null) {
                    TEAMS.putAll(loadedData);
                    for (Identifier id : loadedData.keySet()) {
                        ID_REGISTRY.generateAndBind(id);
                    }
                }
                System.out.println("[Myulib] TeamManager 已成功載入 " + TEAMS.size() + " 筆隊伍資料。");
            }
        });
    }

    public static TeamDefinition register(TeamDefinition team) {
        Objects.requireNonNull(team, "team");
        TEAMS.put(team.id(), team);
        String shortId = ID_REGISTRY.generateAndBind(team.id());
        TEAM_MEMBERS.computeIfAbsent(team.id(), ignored -> new LinkedHashSet<>());
        TEAM_GAME_IDS.remove(team.id());

        // 🌟 寫入儲存庫
        if (storage != null) {
            storage.save(team.id(), team);
        }

        DebugLogManager.log(DebugFeature.TEAM,
                "register id=" + team.id() + ",shortId=" + shortId + ",color=" + team.color());

        return team;
    }

    public static TeamDefinition register(Identifier gameId, TeamDefinition team) {
        Objects.requireNonNull(gameId, "gameId");
        Objects.requireNonNull(team, "team");

        Identifier scopedId = scopedTeamId(gameId, team.id());
        TeamDefinition scoped = new TeamDefinition(scopedId, team.translationKey(), team.color(), team.flags());

        TEAMS.put(scoped.id(), scoped);
        String shortId = ID_REGISTRY.generateAndBind(scoped.id());
        TEAM_MEMBERS.computeIfAbsent(scoped.id(), ignored -> new LinkedHashSet<>());
        TEAM_GAME_IDS.put(scoped.id(), gameId);

        // 🌟 寫入儲存庫
        if (storage != null) {
            storage.save(scoped.id(), scoped);
        }

        DebugLogManager.log(DebugFeature.TEAM,
                "register scoped id=" + scoped.id() + ",shortId=" + shortId + ",game=" + gameId + ",color=" + scoped.color());

        return scoped;
    }

    public static TeamDefinition update(Identifier teamId, UnaryOperator<TeamDefinition> updater) {
        Objects.requireNonNull(teamId, "teamId");
        Objects.requireNonNull(updater, "updater");
        TeamDefinition existing = TEAMS.get(teamId);
        if (existing == null) {
            return null;
        }
        TeamDefinition updated = Objects.requireNonNull(updater.apply(existing), "updated team");
        TEAMS.put(teamId, updated);

        // 🌟 更新儲存庫
        if (storage != null) {
            storage.save(teamId, updated);
        }

        DebugLogManager.log(DebugFeature.TEAM,
                "update id=" + teamId + ",color=" + updated.color());

        return updated;
    }

    public static TeamDefinition unregister(Identifier teamId) {
        TEAM_MEMBERS.remove(teamId);
        TEAM_GAME_IDS.remove(teamId);
        ID_REGISTRY.unbind(teamId);

        PLAYER_TEAM.values().removeIf(id -> Objects.equals(id, teamId));

        // 🌟 從儲存庫刪除
        if (storage != null) {
            storage.delete(teamId);
        }

        DebugLogManager.log(DebugFeature.TEAM,
                "unregister id=" + teamId + ",shortId=" + ID_REGISTRY.getShortId(teamId));

        return TEAMS.remove(teamId);
    }

    public static List<TeamDefinition> unregisterGame(Identifier gameId) {
        Objects.requireNonNull(gameId, "gameId");
        List<TeamDefinition> removed = new java.util.ArrayList<>();

        for (Identifier teamId : new java.util.ArrayList<>(TEAMS.keySet())) {
            if (Objects.equals(TEAM_GAME_IDS.get(teamId), gameId)) {
                // 💡 這裡直接呼叫 unregister(teamId)，所以會自動觸發 storage.delete()，不需要額外寫邏輯
                TeamDefinition team = unregister(teamId);
                if (team != null) {
                    removed.add(team);
                }
            }
        }
        return List.copyOf(removed);
    }

    public static TeamDefinition get(Identifier teamId) {
        return TEAMS.get(teamId);
    }

    public static List<TeamDefinition> all(Identifier gameId) {
        Objects.requireNonNull(gameId, "gameId");
        return TEAMS.entrySet().stream()
                .filter(entry -> Objects.equals(TEAM_GAME_IDS.get(entry.getKey()), gameId))
                .map(Map.Entry::getValue)
                .toList();
    }

    public static Map<Identifier, TeamDefinition> snapshot(Identifier gameId) {
        Objects.requireNonNull(gameId, "gameId");
        Map<Identifier, TeamDefinition> snapshot = new LinkedHashMap<>();
        for (Map.Entry<Identifier, TeamDefinition> entry : TEAMS.entrySet()) {
            if (Objects.equals(TEAM_GAME_IDS.get(entry.getKey()), gameId)) {
                snapshot.put(entry.getKey(), entry.getValue());
            }
        }
        return Map.copyOf(snapshot);
    }

    public static boolean addPlayer(Identifier teamId, UUID playerId) {
        if (!TEAMS.containsKey(teamId) || playerId == null) {
            return false;
        }
        Identifier previous = PLAYER_TEAM.put(playerId, teamId);
        if (previous != null && !previous.equals(teamId)) {
            Set<UUID> previousMembers = TEAM_MEMBERS.get(previous);
            if (previousMembers != null) {
                previousMembers.remove(playerId);
            }
        }
        TEAM_MEMBERS.computeIfAbsent(teamId, ignored -> new LinkedHashSet<>()).add(playerId);
        DebugLogManager.log(DebugFeature.TEAM,
                "add player=" + playerId + " -> team=" + teamId + (previous != null ? ",previous=" + previous : ""));
        return true;
    }

    public static boolean removePlayer(UUID playerId) {
        Identifier teamId = PLAYER_TEAM.remove(playerId);
        if (teamId == null) {
            return false;
        }
        Set<UUID> members = TEAM_MEMBERS.get(teamId);
        if (members != null) {
            members.remove(playerId);
        }
        DebugLogManager.log(DebugFeature.TEAM,
                "remove player=" + playerId + " from team=" + teamId);
        return true;
    }

    public static void forEachMember(Identifier teamId, Consumer<UUID> action) {
        Objects.requireNonNull(teamId, "teamId");
        Objects.requireNonNull(action, "action");
        Set<UUID> members = TEAM_MEMBERS.get(teamId);
        if (members == null) {
            return;
        }
        for (UUID member : Set.copyOf(members)) {
            action.accept(member);
        }
    }

    public static Identifier teamOf(UUID playerId) {
        return PLAYER_TEAM.get(playerId);
    }

    public static Set<UUID> members(Identifier teamId) {
        Set<UUID> members = TEAM_MEMBERS.get(teamId);
        return members == null ? Set.of() : Collections.unmodifiableSet(new LinkedHashSet<>(members));
    }

    public static List<TeamDefinition> all() {
        return List.copyOf(TEAMS.values());
    }

    public static Map<Identifier, TeamDefinition> snapshot() {
        return Map.copyOf(TEAMS);
    }

    public static Map<Identifier, Identifier> teamGameIds() {
        return Map.copyOf(TEAM_GAME_IDS);
    }

    public static Identifier resolveShortId(String shortId) {
        return ID_REGISTRY.getFullId(shortId);
    }

    public static String getShortIdOf(Identifier fullId) {
        return ID_REGISTRY.getShortId(fullId);
    }

    public static Identifier scopedTeamId(@NotNull Identifier gameId, @NotNull Identifier teamId) {
        String prefix = gameId.getPath() + "_";

        if (teamId.getPath().startsWith(prefix) && teamId.getNamespace().equals(gameId.getNamespace())) {
            return teamId;
        }

        return Identifier.fromNamespaceAndPath(gameId.getNamespace(), prefix + teamId.getPath());
    }

    public static void clear() {
        TEAMS.clear();
        TEAM_MEMBERS.clear();
        PLAYER_TEAM.clear();
        TEAM_GAME_IDS.clear();
        ID_REGISTRY.clear();
    }
}