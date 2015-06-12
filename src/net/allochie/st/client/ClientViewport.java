package net.allochie.st.client;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import net.allochie.st.client.render.GLStatic;
import net.allochie.st.shared.math.Vector2;

public class ClientViewport {

	public int width, height;
	public int x, y;
	public Matrix4f persp, look;

	public void updateViewport(int width, int height) {
		this.width = width;
		this.height = height;
		updateGlViewport();
	}

	public Vector2 findMiddle() {
		return new Vector2(x + (width / 2.0d), y + (height / 2.0d));
	}

	private void updateGlViewport() {
		float aspect = (float) width / (float) height;
		GL11.glViewport(0, 0, width, height);
		persp = GLStatic.glPerspective(45.0f, aspect, 0.01f, 100.0f);
		look = GLStatic.glLookAt(0.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}

}
