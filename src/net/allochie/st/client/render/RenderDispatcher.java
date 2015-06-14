package net.allochie.st.client.render;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import net.allochie.st.client.ClientGame;
import net.allochie.st.client.ClientViewport;
import net.allochie.st.client.ClientWorld;
import net.allochie.st.client.render.strategy.StencilBufferStrategy;
import net.allochie.st.client.render.texture.GLPNGTextureLoader;
import net.allochie.st.shared.math.Vector3;
import net.allochie.st.shared.render.ITexture;

public class RenderDispatcher {

	protected GLPNGTextureLoader pngTextureLoader = new GLPNGTextureLoader();
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
		ITexture tex = null, tex2 = null;
		try {
			cursor = pngTextureLoader.getTexture("cursor.png");
			tex = pngTextureLoader.getTexture("test.png");
			tex2 = pngTextureLoader.getTexture("noidea.png");
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
		renderGameWorld(theGame, theGame.viewport, theGame.worldCache);
		renderCursor(theGame);
	}

	private void renderCursor(ClientGame theGame) {
		DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1), ypos = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(theGame.glfwHWindow, xpos, ypos);
		double x = xpos.get(), y = ypos.get();
		Vector3[] ray = RayCast.throwRay(theGame.viewport, (float) x, (float) y, 0.0f, 1.0f);

		GL11.glLineWidth(10.0f);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex3d(ray[0].x, ray[0].y, ray[0].z);
		GL11.glVertex3d(ray[1].x, ray[1].y, ray[1].z);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	private void renderGameWorld(ClientGame theGame, ClientViewport viewport, ClientWorld world) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GLStatic.glWriteColorCube();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	private void renderInterface() {
	}

}
