package net.allochie.st.shared.world.provider;

import java.io.File;
import java.util.HashMap;

import net.allochie.st.shared.init.Blocks;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.ChunkCoord;

public class ChunkProviderServer extends ChunkProvider {

	private HashMap<ChunkCoord, Chunk> chunkCache = new HashMap<ChunkCoord, Chunk>();

	public ChunkProviderServer(File savePath) {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Chunk getChunkForCoords(ChunkCoord coord) {
		if (chunkCache.containsKey(coord))
			return chunkCache.get(coord);
		Chunk genChunk = generateChunkForCoords(coord);
		if (genChunk != null)
			chunkCache.put(coord, genChunk);
		return genChunk;
	}

	private Chunk generateChunkForCoords(ChunkCoord coord) {
		if (0 > coord.x || 0 > coord.y)
			return null;
		if (coord.x * world.chunkWidth > world.worldWidth || coord.y * world.chunkHeight > world.worldHeight)
			return null;
		Chunk achunk = new Chunk(world.chunkWidth, world.chunkHeight);
		achunk.setPosition(coord);
		for (int x = 0; x < achunk.width; x++)
			for (int y = 0; y < achunk.height; y++) {
				achunk.blocks[x][y] = Blocks.foundation.blockid;
			}
		return achunk;
	}

}
