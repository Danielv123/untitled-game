package net.allochie.st.client.render;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.allochie.st.client.ClientGame;
import net.allochie.st.client.ClientViewport;
import net.allochie.st.client.ClientWorld;
import net.allochie.st.client.render.strategy.StencilBufferStrategy;
import net.allochie.st.client.render.texture.GLPNGTextureLoader;
import net.allochie.st.client.render.world.ClientWorldRenderer;
import net.allochie.st.shared.init.Blocks;
import net.allochie.st.shared.math.AABB;
import net.allochie.st.shared.math.Ray3;
import net.allochie.st.shared.math.Vector3;
import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.world.Block;

public class RenderDispatcher {

	protected GLPNGTextureLoader pngTextureLoader = new GLPNGTextureLoader();
	protected ClientWorldRenderer worldRenderer = new ClientWorldRenderer();
	protected StencilBufferStrategy stencilBuffer;
	protected ITexture cursor = null;

	public void prepare(ClientGame theGame) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		GL11.glClearDepth(1.0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.0001F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

		stencilBuffer = new StencilBufferStrategy(800, 600);
		try {
			cursor = pngTextureLoader.getTexture("cursor.png");
			Blocks.initBlocks(pngTextureLoader);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void gameResized(ClientGame theGame, int width, int height) {
		if (stencilBuffer != null)
			stencilBuffer.updateResolution(width, height);
	}

	public void renderGame(ClientGame theGame) {
		GL11.glClearColor(0.33f, 0.33f, 0.33f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		theGame.viewport.updateCamera();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GLStatic.glWriteAxis();

		worldRenderer.renderWorld(theGame);
		renderCursor(theGame);
	}

	private void renderCursor(ClientGame theGame) {
		double x = Mouse.getX(), y = Mouse.getY();
		Vector3[] ray = RayCast.throwRay(theGame.viewport, (float) x, (float) y, 0.0f, 1.0f);
		Vector3 rz = ray[0];
		cursor.bind();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(cursor.u0(), cursor.v0());
		GL11.glVertex3d(rz.x, rz.y, rz.z);
		GL11.glTexCoord2f(cursor.u0(), cursor.v1());
		GL11.glVertex3d(rz.x, rz.y - 0.01d, rz.z);
		GL11.glTexCoord2f(cursor.u1(), cursor.v1());
		GL11.glVertex3d(rz.x + 0.01d, rz.y - 0.01d, rz.z);
		GL11.glTexCoord2f(cursor.u1(), cursor.v0());
		GL11.glVertex3d(rz.x + 0.01d, rz.y, rz.z);
		GL11.glEnd();
		cursor.release();
	}

	private void renderGameWorld(ClientGame theGame, ClientViewport viewport, ClientWorld world) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Vector3[] ray = RayCast.throwRay(theGame.viewport, (float) Mouse.getX(), (float) Mouse.getY(), 0.0f, 1.0f);
		Ray3 gameRay = Ray3.fromLine(ray[0], ray[1]);

		for (int x = -9; x <= 9; x++) {
			for (int y = -9; y <= 9; y++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(x * 2.4f, y * 2.4f, -2.0f);
				AABB box = new AABB(new Vector3((x * 2.4f) - 1.0f, (y * 2.4f) - 1.0f, -3.0f), new Vector3(
						(x * 2.4f) + 1.0f, (y * 2.4f) + 1.0f, -1.0f));
				if (box.intersectsRay(gameRay, 0.0f, 9999999.0f) == null)
					GLStatic.glWriteColorCube();
				GL11.glPopMatrix();
			}
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void renderInterface() {
	}

}
