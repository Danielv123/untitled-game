package net.allochie.st.shared.world;

import java.util.HashMap;
import java.util.Iterator;

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

	public static void initBlocks(ITextureProvider textures) {
		Iterator<Block> types = blockTypes.values().iterator();
		while (types.hasNext())
			types.next().initializeTextures(textures);
	}

	/** The block ID */
	public final int blockid;

	public Block(int blockid) {
		this.blockid = blockid;
		Block.blockTypes.put(blockid, this);
	}

	public abstract void initializeTextures(ITextureProvider provider);

	public abstract boolean renderBack(IWorldAccess world, int x, int y);

	public abstract ITexture getRenderTexture(IWorldAccess world, int x, int y);

	public abstract boolean[] renderWalls(IWorldAccess world, int x, int y);

	public abstract ITexture[] getWallTexture(IWorldAccess world, int x, int y);

	public abstract boolean renderCeiling(IWorldAccess world, int x, int y);

	public abstract ITexture getCeilingTexture(IWorldAccess world, int x, int y);

}
