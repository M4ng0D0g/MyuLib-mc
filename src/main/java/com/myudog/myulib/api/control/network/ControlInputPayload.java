package com.myudog.myulib.api.control.network;

import com.myudog.myulib.Myulib;
import net.minecraft.resources.Identifier;

/**
 * 玩家遙控實體的輸入封包資料
 */
public record ControlInputPayload(
        boolean up,
        boolean down,
        boolean left,
        boolean right,
        boolean jumping,
        boolean sneaking,
        float yaw,   // 玩家當前的視角 X 軸旋轉 (滑鼠左右)
        float pitch  // 玩家當前的視角 Y 軸旋轉 (滑鼠上下)
) {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(Myulib.MOD_ID, "control_input");

    // 💡 註：實務上你需要將這個 Record 註冊進 Fabric 的 PacketRegistry
    // (例如使用 ClientPlayNetworking.send() 與 ServerPlayNetworking.registerGlobalReceiver())
    // 這裡我們先定義好它要傳遞的資料結構。
}