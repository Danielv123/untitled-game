package net.allochie.st.client.glfw;

import net.allochie.st.client.ClientGame;

import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyCallbackImpl extends GLFWKeyCallback {

	private final ClientGame theGame;

	public KeyCallbackImpl(ClientGame theGame) {
		this.theGame = theGame;
	}

	@Override
	public void invoke(long window, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
	}

}
