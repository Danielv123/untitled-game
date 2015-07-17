package net.allochie.st.shared.world.provider;

import java.util.ArrayList;
import java.util.HashMap;

import net.allochie.st.client.ClientGame;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.ChunkCoord;

public class ChunkProviderClient extends ChunkProvider {

	private final ClientGame game;

	private String remoteWorldName;
	private int width, height;

	private HashMap<ChunkCoord, Chunk> chunkCache = new HashMap<ChunkCoord, Chunk>();
	private ArrayList<ChunkCoord> pendingChunks = new ArrayList<ChunkCoord>();

	public ChunkProviderClient(ClientGame game, String remoteWorldName, int width, int height) {
		super();
		this.game = game;
		this.remoteWorldName = remoteWorldName;
		this.width = width;
		this.height = height;
	}

	@Override
	public Chunk getChunkForCoords(ChunkCoord coord) {
		if (chunkCache.containsKey(coord))
			return chunkCache.get(coord);
		if (!pendingChunks.contains(coord)) {
			pendingChunks.add(coord);
			game.currentPlayer.requestChunk(coord);
		}
		return null;
	}

	public void fillInChunk(ChunkCoord coord, int width, int height, int[][] blocks, int[][] blockData) {
		Chunk aChunk = new Chunk(width, height);
		aChunk.setPosition(coord);
		aChunk.fillFromData(blocks, blockData);
		chunkCache.put(aChunk.getPosition(), aChunk);
	}

}
