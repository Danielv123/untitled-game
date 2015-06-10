package net.allochie.st.shared.world;

import net.allochie.st.shared.math.Vector2;

/**
 * A tile in the game
 * 
 * @author AfterLifeLochie
 *
 */
public abstract class Tile {

	/** The world access */
	private IWorldAccess world;
	/** The tile position */
	private Vector2 position;

	/**
	 * Get the current world access object
	 * 
	 * @return The current world access object
	 */
	public IWorldAccess getWorld() {
		return world;
	}

	/**
	 * Get the current position
	 * 
	 * @return The current position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Set the current world access object
	 * 
	 * @param world
	 *            The new world access object
	 */
	public void setWorldAndPosition(IWorldAccess world, Vector2 position) {
		this.world = world;
		this.position = position;
	}

	/** Called by the client to perform client-side logic. */
	public abstract void thinkClient();

	/** Called by the server to perform server-side logic */
	public abstract void thinkServer();

}
