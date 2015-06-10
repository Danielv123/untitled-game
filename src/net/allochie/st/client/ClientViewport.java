package net.allochie.st.client;

import net.allochie.st.shared.math.Vector2;

public class ClientViewport {

	private int width, height;
	private int x, y;

	public Vector2 findMiddle() {
		return new Vector2(x + (width / 2.0d), y + (height / 2.0d));
	}

}
