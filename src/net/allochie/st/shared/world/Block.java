package net.allochie.st.shared.world;

import java.util.HashMap;

import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.render.ITextureProvider;

/**
 * A block in the game.
 * 
 * @author AfterLifeLochie
 *
 */
public abstract class Block {

	private static final HashMap<Integer, Block> blockTypes = new HashMap<Integer, Block>();

	public static Block getBlockByType(int type) {
		return blockTypes.get(type);
	}

	public static int getTypeOfBlock(Block block) {
		return block.blockid;
	}

	/** The block ID */
	public final int blockid;

	public Block(int blockid) {
		this.blockid = blockid;
		Block.blockTypes.put(blockid, this);
	}

	public abstract void initializeTextures(ITextureProvider provider);

	public abstract ITexture getRenderTexture(IWorldAccess world, int x, int y);

}
