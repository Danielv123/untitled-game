package net.allochie.st.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.allochie.st.client.render.GLStatic;
import net.allochie.st.shared.math.Vector2;

public class ClientViewport {

	public int width, height;
	public int x, y;

	public void updateViewport(int width, int height) {
		this.width = width;
		this.height = height;
		updateGlViewport();
	}

	public Vector2 findMiddle() {
		return new Vector2(x + (width / 2.0d), y + (height / 2.0d));
	}

	private void updateGlViewport() {
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float aspect = (float) width / (float) height;
		GLU.gluPerspective(45.0f, aspect, 1.0f, 200.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
}
