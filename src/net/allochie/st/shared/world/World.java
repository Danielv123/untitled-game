package net.allochie.st.shared.world;

import java.util.List;

import net.allochie.st.shared.world.provider.ChunkProvider;

public class World implements IWorldAccess {

	private int worldHeight;
	private int worldWidth;

	private List<Chunk> chunks;
	private ChunkProvider provider;

	public World() {
	}

	public void thinkClient() {
	}

	public void thinkServer() {
	}

	public Chunk getChunkForBlockCoords(int x, int y) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Send change to client

	}

	@Override
	public Tile getBlockTile(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
