package net.allochie.st.shared.world;

/**
 * A block in the game.
 * 
 * @author AfterLifeLochie
 *
 */
public abstract class Block {

	/** The block ID */
	public final int blockid;

	public Block(int blockid) {
		this.blockid = blockid;
	}

}
