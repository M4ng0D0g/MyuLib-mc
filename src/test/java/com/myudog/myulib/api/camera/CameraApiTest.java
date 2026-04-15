package com.myudog.myulib.api.camera;

import com.myudog.myulib.api.animation.Easing;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

final class CameraApiTest {

    @AfterEach
    void resetBridge() {
        CameraDispatchBridge.setLocalHandler(null);
    }

    @Test
    void localCameraActionsDispatchExpectedPayloads() {
        AtomicReference<CameraActionPayload> captured = new AtomicReference<>();
        CameraDispatchBridge.setLocalHandler(captured::set);

        CameraApi.shakeLocal(1.25f, 500L);
        CameraActionPayload shake = captured.get();
        assertNotNull(shake, "shakeLocal should dispatch a payload");
        assertEquals(CameraActionPayload.ActionType.SHAKE, shake.action(), "shakeLocal should dispatch SHAKE action");
        assertEquals(1.25f, shake.intensity(), "shakeLocal should preserve intensity");

        CameraTrackingTarget target = CameraTrackingTarget.of(new Vec3(3, 4, 5)).withOffset(new Vec3(1, 0, -1));
        CameraApi.moveToLocal(target, 300L, Easing.LINEAR);
        CameraActionPayload move = captured.get();
        assertEquals(CameraActionPayload.ActionType.MOVE_TO, move.action(), "moveToLocal should dispatch MOVE_TO action");
        assertEquals(new Vec3(3, 4, 5), move.targetStaticPos(), "MOVE_TO payload should carry target position");
        assertEquals(new Vec3(1, 0, -1), move.offset(), "MOVE_TO payload should carry offset");

        CameraApi.resetLocal();
        CameraActionPayload reset = captured.get();
        assertEquals(CameraActionPayload.ActionType.RESET, reset.action(), "resetLocal should dispatch RESET action");
    }
}

