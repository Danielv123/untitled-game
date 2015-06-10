package net.allochie.st.shared.world;

import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.render.ITextureProvider;

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

	public abstract void initializeTextures(ITextureProvider provider);

	public abstract ITexture getRenderTexture(IWorldAccess world, int x, int y);

}
