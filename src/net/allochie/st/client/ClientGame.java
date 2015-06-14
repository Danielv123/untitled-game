package net.allochie.st.client;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import net.allochie.st.client.glfw.KeyCallbackImpl;
import net.allochie.st.client.glfw.MouseButtonCallbackImpl;
import net.allochie.st.client.glfw.WindowSizeCallbackImpl;
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

	public GLContext glContext;
	public GLFWErrorCallback glfwErrorCallback;
	public final GLFWWindowSizeCallback glfwSizeCallback = new WindowSizeCallbackImpl(this);
	public final GLFWKeyCallback glfwKeyboard = new KeyCallbackImpl(this);
	public final GLFWMouseButtonCallback glfwMouse = new MouseButtonCallbackImpl(this);
	public long glfwHWindow;

	public ClientGame() {
		this.thinkThread = new ThinkerThread();
		thinkThread.startThread(false);
		init();
		loop();
	}

	@Override
	public void poll(String label) {
		try {
			glContext.checkGLError();
		} catch (Throwable t) {
			System.out.println("OpenGL error detected: " + label);
			t.printStackTrace();
			System.exit(9001);
		}
	}

	private void init() {
		GLFW.glfwSetErrorCallback(glfwErrorCallback = Callbacks.errorCallbackPrint(System.err));

		if (GLFW.glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);

		int WIDTH = 800;
		int HEIGHT = 600;

		glfwHWindow = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", 0, 0);
		if (glfwHWindow == 0)
			throw new RuntimeException("Failed to create the GLFW window");

		GLFW.glfwSetKeyCallback(glfwHWindow, glfwKeyboard);
		GLFW.glfwSetMouseButtonCallback(glfwHWindow, glfwMouse);
		GLFW.glfwSetWindowSizeCallback(glfwHWindow, glfwSizeCallback);

		ByteBuffer vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(glfwHWindow, (GLFWvidmode.width(vidmode) - WIDTH) / 2,
				(GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		GLFW.glfwMakeContextCurrent(glfwHWindow);
		// GLFW.glfwSetInputMode(glfwHWindow, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(glfwHWindow);
	}

	public void resizeApplication(int width, int height) {
		if (height <= 0)
			height = 1;
		viewport.updateViewport(width, height);
		renderer.gameResized(this, width, height);
	}

	private void loop() {
		glContext = GLContext.createFromCurrent();
		resizeApplication(800, 600);
		renderer.prepare(this);
		while (GLFW.glfwWindowShouldClose(glfwHWindow) == GL11.GL_FALSE) {
			poll("render doFrame");
			renderer.renderGame(this);
			GLFW.glfwSwapBuffers(glfwHWindow);
			GLFW.glfwPollEvents();
			poll("render doneFrame");
		}
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
