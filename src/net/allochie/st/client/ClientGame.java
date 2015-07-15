package net.allochie.st.client;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Util;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.allochie.st.client.render.IRenderContext;
import net.allochie.st.client.render.RenderDispatcher;
import net.allochie.st.client.screens.IScreen;
import net.allochie.st.shared.system.ThinkerThread;

public class ClientGame implements IRenderContext {

	public IScreen gameScreen;
	public ClientViewport viewport = new ClientViewport();
	public ClientWorld worldCache;
	public ThinkerThread thinkThread;
	public RenderDispatcher renderer = new RenderDispatcher();

	public ClientGame() {
		try {
			this.thinkThread = new ThinkerThread();
			thinkThread.startThread(false);
			init();
			loop();
		} catch (LWJGLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void poll(String label) {
		try {
			Util.checkGLError();
		} catch (Throwable t) {
			System.out.println("OpenGL error detected: " + label);
			t.printStackTrace();
			System.exit(9001);
		}
	}

	private void init() throws LWJGLException {
		int WIDTH = 800;
		int HEIGHT = 600;
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setResizable(true);
		Display.create();
		Mouse.create();
		Keyboard.create();

		Mouse.setNativeCursor(new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null));
	}

	public void resizeApplication(int width, int height) {
		if (height <= 0)
			height = 1;
		viewport.updateViewport(width, height);
		renderer.gameResized(this, width, height);
	}

	private void loop() {
		resizeApplication(800, 600);
		renderer.prepare(this);
		while (!Display.isCloseRequested()) {
			if (Display.wasResized())
				viewport.updateViewport(Display.getWidth(), Display.getHeight());
			poll("render doFrame");
			renderer.renderGame(this);
			poll("render doneFrame");
			Display.update();
			Display.sync(120);
		}
		Display.destroy();
		Mouse.destroy();
		Keyboard.destroy();
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
