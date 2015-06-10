package net.allochie.st.client;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import net.allochie.st.shared.system.ThinkerThread;

public class ClientGame {

	private ClientViewport viewport;
	private ClientWorld worldCache;
	private ThinkerThread thinkThread;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	private long window;

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
		GLFW.glfwSetErrorCallback(errorCallback = Callbacks
				.errorCallbackPrint(System.err));

		if (GLFW.glfwInit() != GL11.GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");

		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);

		int WIDTH = 300;
		int HEIGHT = 300;

		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", 0, 0);
		if (window == 0)
			throw new RuntimeException("Failed to create the GLFW window");

		GLFW.glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action,
					int mods) {
				if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
					GLFW.glfwSetWindowShouldClose(window, GL11.GL_TRUE);
			}
		});

		ByteBuffer vidmode = GLFW
				.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - WIDTH) / 2,
				(GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(window);
	}

	private void loop() {
		GLContext.createFromCurrent();
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
