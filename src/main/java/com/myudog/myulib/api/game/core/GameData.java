package com.myudog.myulib.api.game.core;

import com.myudog.myulib.api.game.object.IGameEntity;
import com.myudog.myulib.api.timer.TimerManager;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class GameData {

    // 🌟 核心：移除所有靜態的 ID 生成邏輯，改為依賴注入
    private Identifier id;
    private String shortId;

    private final Set<Integer> timerInstanceIds = new LinkedHashSet<>();
    private final Map<Integer, String> timerTags = new LinkedHashMap<>();
    private final Set<IGameEntity> activeEntities = new LinkedHashSet<>();
    private final List<String> scoreboardLines = new ArrayList<>();
    private final Map<String, Integer> scoreboardValues = new LinkedHashMap<>();

    protected GameData() {
        // 建構子不再自行產生 ID，由 GameManager 創建 Instance 時分配
    }

    /**
     * 🌟 供 GameManager 或 Storage 載入時呼叫，注入唯一識別碼
     */
    public void setupId(Identifier id, String shortId) {
        this.id = id;
        this.shortId = shortId;
    }

    public Identifier getId() {
        return this.id;
    }

    public String getShortId() {
        return this.shortId;
    }

    public int startNewTimer(String tag, long ticks, Consumer<Integer> onExpire) {
        int id = TimerManager.start(ticks, onExpire);
        this.timerInstanceIds.add(id);
        this.timerTags.put(id, tag);
        return id;
    }

    public void reset() {
        timerInstanceIds.clear();
        timerTags.clear();
        activeEntities.clear();
        scoreboardLines.clear();
        scoreboardValues.clear();
    }

    public final Set<Integer> timerInstanceIds() { return timerInstanceIds; }
    public final Map<Integer, String> timerTags() { return timerTags; }
    public final Set<IGameEntity> activeEntities() { return activeEntities; }
    public final List<String> scoreboardLines() { return scoreboardLines; }
    public final Map<String, Integer> scoreboardValues() { return scoreboardValues; }
}