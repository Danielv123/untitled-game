package net.allochie.st.shared.world;

import java.util.List;

import net.allochie.st.shared.world.provider.ChunkProvider;

public class World implements IWorldAccess {

	private List<Chunk> chunks;
	private ChunkProvider provider;

	public World() {
	}

	@Override
	public void setBlockInWorld(int x, int y, Block block, int data) {
		// TODO Auto-generated method stub

	}

	@Override
	public Block getBlockInWorld(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBlockDataInWorld(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markBlockForUpdate(int x, int y) {
		// TODO Auto-generated method stub

	}

}