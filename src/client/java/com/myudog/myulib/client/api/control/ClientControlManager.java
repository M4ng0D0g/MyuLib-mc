package com.myudog.myulib.client.api.control;

import com.myudog.myulib.api.control.network.ControlInputPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class ClientControlManager {

    // 記錄客戶端當前是否處於遙控狀態
    private static boolean isControlling = false;

    private ClientControlManager() {}

    public static void setControlling(boolean controlling) {
        isControlling = controlling;
    }

    public static boolean isControlling() {
        return isControlling;
    }

    /**
     * 負責將攔截到的按鍵打包並發送給伺服器
     */
    public static void sendInput(boolean up, boolean down, boolean left, boolean right, boolean jumping, boolean sneaking) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        // 取得玩家當前滑鼠轉動的視角
        float yaw = player.getYRot();
        float pitch = player.getXRot();

        ControlInputPayload payload = new ControlInputPayload(
                up, down, left, right, jumping, sneaking, yaw, pitch
        );

        // 透過 Fabric API 發送給伺服器 (需配合你的網路註冊機制)
        // ClientPlayNetworking.send(ControlInputPayload.ID, payload 的 ByteBuf 或 Codec);
    }
}