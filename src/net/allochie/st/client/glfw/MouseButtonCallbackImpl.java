package net.allochie.st.client.glfw;

import net.allochie.st.client.ClientGame;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonCallbackImpl extends GLFWMouseButtonCallback {

	private final ClientGame theGame;

	public MouseButtonCallbackImpl(ClientGame theGame) {
		this.theGame = theGame;
	}

	@Override
	public void invoke(long arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

}
