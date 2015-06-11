package net.allochie.st.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import net.allochie.st.client.render.GLStatic;
import net.allochie.st.client.render.IRenderContext;
import net.allochie.st.client.render.strategy.StencilBufferStrategy;
import net.allochie.st.client.render.texture.GLPNGTextureLoader;
import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.system.ThinkerThread;

public class ClientGame implements IRenderContext {

	private ClientViewport viewport;
	private ClientWorld worldCache;
	private ThinkerThread thinkThread;

	private GLContext glContext;
	private GLFWErrorCallback glfwErrorCallback;
	private GLFWWindowSizeCallback glfwSizeCallback;
	private GLFWKeyCallback glfwKeyboard;
	private GLFWMouseButtonCallback glfwMouse;
	private long glfwHWindow;

	private StencilBufferStrategy buffer;

	public ClientGame() {
		this.thinkThread = new ThinkerThread();
		thinkThread.startThread(false);
		init();
		loop();
	}

	public ClientViewport getViewport() {
		return viewport;
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

		GLFW.glfwSetWindowSizeCallback(glfwHWindow, glfwSizeCallback = new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int w, int h) {
				// TODO Auto-generated method stub
				if (glContext != null) {
					resizeApplication(w, h);
				}
			}
		});

		ByteBuffer vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(glfwHWindow, (GLFWvidmode.width(vidmode) - WIDTH) / 2,
				(GLFWvidmode.height(vidmode) - HEIGHT) / 2);
		GLFW.glfwMakeContextCurrent(glfwHWindow);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(glfwHWindow);
	}

	private void resizeApplication(int width, int height) {
		if (height == 0)
			height = 1;
		float aspect = (float) width / (float) height;
		GL11.glViewport(0, 0, width, height);
		GLStatic.glPerspective(45.0f, aspect, 0.01f, 100.0f);
		GLStatic.glLookAt(-5.0f, 5.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		buffer.updateResolution(width, height);
	}

	private void loop() {
		glContext = GLContext.createFromCurrent();
		buffer = new StencilBufferStrategy(800, 600);
		resizeApplication(800, 600);

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

		GLPNGTextureLoader loader = new GLPNGTextureLoader();
		ITexture tex = null, tex2 = null;
		try {
			tex = loader.getTexture("test.png");
			tex2 = loader.getTexture("noidea.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		float frame = 0;

		while (GLFW.glfwWindowShouldClose(glfwHWindow) == GL11.GL_FALSE) {
			poll("render doFrame");

			GL11.glClearColor(0.33f, 0.33f, 0.33f, 1.0f);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			buffer.enter(this);
			poll("render mountBuffer");
			GL11.glPushMatrix();
			GL11.glClearColor(0.66f, 0.66f, 0.66f, 1.0f);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			GLStatic.glLookAt(1.5f, 1.5f, 1.5f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
			tex.bind();
			GL11.glRotatef(frame, 0.0f, 1.0f, 0.0f);
			GLStatic.glWriteCube();
			tex.release();
			GL11.glPopMatrix();
			poll("render closeBuffer");
			buffer.exit(this);

			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			frame++;

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, buffer.texture);
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					GL11.glPushMatrix();
					GL11.glTranslatef((i - 2) * 2.22f, (j - 2) * 2.22f, 0.0f);
					GLStatic.glWriteWall();
					GL11.glPopMatrix();
				}
			}

			for (int i = 0; i < 12; i++) {
				GL11.glPushMatrix();
				tex2.bind();
				int t = ((int) frame % 60) - 30;
				GL11.glTranslatef((i - 6) * 2.12f, t * 0.12f, 0.44f);
				GLStatic.glWriteWall();
				tex2.release();
				GL11.glPopMatrix();
			}

			// GLStatic.glWriteColorCube();
			// GL11.glColor3f(1.0f, 1.0f, 1.0f);
			poll("render doneFrame");

			GLFW.glfwSwapBuffers(glfwHWindow);
			GLFW.glfwPollEvents();
			poll("render doneAllFrame");
		}
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
