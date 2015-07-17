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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChunkCoord{").append(x).append(", ").append(y).append("}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ChunkCoord))
			return false;
		ChunkCoord that = (ChunkCoord) o;
		return that.x == this.x && that.y == this.y;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = 31 * hash + x;
		hash = 31 * hash + y;
		return hash;
	}

}
