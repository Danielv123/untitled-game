package net.allochie.st.client;

import java.io.File;
import java.io.IOException;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Util;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.allochie.st.client.pe.PEResourceExtractor;
import net.allochie.st.client.render.IRenderContext;
import net.allochie.st.client.render.RenderDispatcher;
import net.allochie.st.client.screens.IScreen;
import net.allochie.st.server.ServerGameSession;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.system.ThinkerThread;
import net.allochie.st.shared.world.provider.ChunkProviderClient;

public class ClientGame implements IRenderContext {

	public ThinkerThread thinkThread;

	public IScreen gameScreen;
	public ClientViewport viewport = new ClientViewport();
	public RenderDispatcher renderer = new RenderDispatcher();

	public ClientWorld worldCache;
	public ChunkProviderClient chunkCache;

	private Thread serverThread;
	private NetworkManager clientNetwork;

	public ClientPlayer currentPlayer;

	public ClientGame() {
		try {

			try {
				new PEResourceExtractor(new File("binary/SIMTOWER.exe")).prepareAll();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

			this.thinkThread = new ThinkerThread();
			thinkThread.startThread(false);

			serverThread = new Thread(new Runnable() {
				@Override
				public void run() {
					new ServerGameSession(new File("./server"));
				}
			});
			serverThread.start();

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

		clientNetwork = new NetworkManager();
		thinkThread.addThinker(clientNetwork);
		clientNetwork.spinUpClient(this, "localhost", 9000);
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
		clientNetwork.shutdown();
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
