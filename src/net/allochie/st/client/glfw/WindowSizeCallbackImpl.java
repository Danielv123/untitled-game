package net.allochie.st.client.glfw;

import net.allochie.st.client.ClientGame;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

public class WindowSizeCallbackImpl extends GLFWWindowSizeCallback {

	private final ClientGame theGame;

	public WindowSizeCallbackImpl(ClientGame theGame) {
		this.theGame = theGame;
	}

	@Override
	public void invoke(long window, int w, int h) {
		if (theGame.glContext != null) {
			theGame.resizeApplication(w, h);
			theGame.renderer.gameResized(theGame, w, h);
		}
	}

}
