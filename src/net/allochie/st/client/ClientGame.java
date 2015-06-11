package net.allochie.st.client;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import net.allochie.st.shared.system.ThinkerThread;

public class ClientGame {

	private ClientViewport viewport;
	private ClientWorld worldCache;
	private ThinkerThread thinkThread;

	private GLFWErrorCallback glfwErrorCallback;
	private GLFWKeyCallback glfwKeyboard;
	private GLFWMouseButtonCallback glfwMouse;
	private long glfwHWindow;

	public ClientGame() {
		this.thinkThread = new ThinkerThread();
		thinkThread.startThread(false);
		init();
		loop();
	}

	public ClientViewport getViewport() {
		return viewport;
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

		GLFW.glfwSetKeyCallback(glfwHWindow, glfwKeyboard = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
					GLFW.glfwSetWindowShouldClose(window, GL11.GL_TRUE);
			}
		});

		GLFW.glfwSetMouseButtonCallback(glfwHWindow, glfwMouse = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}
		});

		ByteBuffer vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(glfwHWindow, (GLFWvidmode.width(vidmode) - WIDTH) / 2,
				(GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		GLFW.glfwMakeContextCurrent(glfwHWindow);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(glfwHWindow);
	}

	private void loop() {
		GLContext.createFromCurrent();
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		while (GLFW.glfwWindowShouldClose(glfwHWindow) == GL11.GL_FALSE) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GLFW.glfwSwapBuffers(glfwHWindow);
			GLFW.glfwPollEvents();
		}
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
