package net.allochie.st.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.allochie.st.client.render.GLStatic;
import net.allochie.st.shared.math.Vector2;
import net.allochie.st.shared.math.Vector3;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;

public class ClientViewport {

	public int width, height;
	public float x, y, zoom;

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

	public void moveCamera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void updateCamera() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			y += 0.1f;
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			y -= 0.1f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			x -= 0.1f;
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			x += 0.1f;

		int dWheel = Mouse.getDWheel();
		if (dWheel < 0)
			zoom += 0.5f;
		else if (dWheel > 0 && zoom > 0.0f)
			zoom -= 0.5f;

		GL11.glLoadIdentity();
		GLU.gluLookAt(x, y, 0.01f + zoom, x, y, 0.0f, 0.0f, 1.0f, 0.0f);
	}

	public Vector2 chunkCoordsToCamera(World aworld, Chunk achunk) {
		Vector2 origin = new Vector2(achunk.getPosition().x * aworld.chunkWidth, achunk.getPosition().y
				* aworld.chunkHeight);
		return origin.sub(new Vector2(x, y));
	}
}
