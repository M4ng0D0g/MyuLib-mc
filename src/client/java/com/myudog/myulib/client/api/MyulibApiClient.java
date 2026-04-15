package com.myudog.myulib.client.api;

import com.myudog.myulib.client.api.camera.ClientCameraBridge;
import com.myudog.myulib.client.api.field.FieldVisualizationClientRenderer;

public class MyulibApiClient {
	private MyulibApiClient() {
	}

	public static void init() {
		ClientCameraBridge.installBridge();
		FieldVisualizationClientRenderer.install();
	}
}
