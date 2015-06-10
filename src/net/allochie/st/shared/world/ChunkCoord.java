package net.allochie.st.shared.world;

import net.allochie.st.shared.math.Vector2;

public class ChunkCoord {

	public final int x;
	public final int y;

	public ChunkCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 toVector2() {
		return new Vector2((double) x, (double) y);
	}

}
