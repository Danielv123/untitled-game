package net.allochie.st.shared.world;

import java.util.ArrayList;
import java.util.List;

import net.allochie.st.shared.math.Vector2;
import net.allochie.st.shared.system.IThink;
import net.allochie.st.shared.world.provider.ChunkProvider;

public class World implements IWorldAccess, IThink {

	public static final int defaultChunkWidth = 32;
	public static final int defaultChunkHeight = 256;

	protected boolean isServerWorld;

	public String worldName;
	public int worldWidth, worldHeight;
	public int chunkWidth = defaultChunkWidth, chunkHeight = defaultChunkHeight;

	private List<Chunk> chunks = new ArrayList<Chunk>();
	private ChunkProvider provider;

	public World(ChunkProvider provider) {
		this.provider = provider;
		this.isServerWorld = true;
		provider.setWorld(this);
	}

	@Override
	public void thinkClient() {
	}

	@Override
	public void thinkServer() {
	}

	public ChunkCoord getChunkCoordsForBlockCoords(int x, int y) {
		return new ChunkCoord((int) Math.floor(x / (double) chunkWidth), (int) Math.floor(y / (double) chunkHeight));
	}

	public Vector2 getBlockCoordsInChunk(ChunkCoord coord, int bx, int by) {
		return new Vector2(bx - coord.x, by - coord.y);
	}

	public Chunk getChunkFromBlockCoords(int x, int y) {
		return getChunkFromCoords(getChunkCoordsForBlockCoords(x, y));
	}

	public Chunk getChunkFromCoords(ChunkCoord coords) {
		for (Chunk chunk : chunks)
			if (chunk.getPosition().equals(coords))
				return chunk;
		Chunk zz = provider.getChunkForCoords(coords);
		if (zz != null)
			chunks.add(zz);
		return zz;
	}

	public Chunk[] getAdjacentChunks(Chunk gameChunk) {
		ChunkCoord coord = gameChunk.getPosition();
		return new Chunk[] { getChunkFromCoords(new ChunkCoord(coord.x - 1, coord.y)),
				getChunkFromCoords(new ChunkCoord(coord.x + 1, coord.y)) };
	}

	@Override
	public void setBlockInWorld(int x, int y, Block block, int data) {
		ChunkCoord coord = getChunkCoordsForBlockCoords(x, y);
		Chunk chunk = getChunkFromCoords(coord);
		Vector2 blockCoords = getBlockCoordsInChunk(coord, x, y);
		boolean update = chunk.setBlock(blockCoords, block, data);
		if (update)
			markBlockForUpdate(x, y);
	}

	@Override
	public Block getBlockInWorld(int x, int y) {
		ChunkCoord coord = getChunkCoordsForBlockCoords(x, y);
		Chunk chunk = getChunkFromCoords(coord);
		Vector2 blockCoords = getBlockCoordsInChunk(coord, x, y);
		return chunk.getBlock(blockCoords);
	}

	@Override
	public int getBlockDataInWorld(int x, int y) {
		ChunkCoord coord = getChunkCoordsForBlockCoords(x, y);
		Chunk chunk = getChunkFromCoords(coord);
		Vector2 blockCoords = getBlockCoordsInChunk(coord, x, y);
		return chunk.getBlockData(blockCoords);
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
