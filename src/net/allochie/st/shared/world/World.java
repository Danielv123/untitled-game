package net.allochie.st.shared.world;

import java.util.List;

import net.allochie.st.shared.math.Vector2;
import net.allochie.st.shared.world.provider.ChunkProvider;

public class World implements IWorldAccess {

	protected int worldHeight;
	protected int worldWidth;

	private List<Chunk> chunks;
	private ChunkProvider provider;

	public World(ChunkProvider provider) {
		this.provider = provider;
		provider.setWorld(this);
	}

	public void thinkClient() {
	}

	public void thinkServer() {
	}

	public ChunkCoord getChunkCoordsForBlockCoords(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector2 getBlockCoordsInChunk(ChunkCoord coord, int bx, int by) {
		// TODO Auto-generated method stub
		return null;
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
		Chunk chunk = getChunkForBlockCoords(x, y);
		// TODO Access chunk to get block
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
