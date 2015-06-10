package net.allochie.st.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.allochie.st.shared.math.Vector2;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;
import net.allochie.st.shared.world.provider.ChunkProvider;

public class ClientWorld extends World {

	private ClientGame client;
	private List<Chunk> dirtyChunks;
	private DirtyChunkSorter dirtyChunkSorter;

	public ClientWorld(ClientGame client, ChunkProvider provider) {
		super(provider);
		this.client = client;
		this.isServerWorld = false;
		dirtyChunks = new ArrayList<Chunk>();
		dirtyChunkSorter = new DirtyChunkSorter(client.getViewport());
	}

	@Override
	public void markBlockForUpdate(int x, int y) {
		Chunk chunk = getChunkFromBlockCoords(x, y);
		if (!dirtyChunks.contains(chunk))
			dirtyChunks.add(chunk);
	}

	private static class DirtyChunkSorter implements Comparator<Chunk> {
		private final ClientViewport viewport;

		public DirtyChunkSorter(ClientViewport viewport) {
			this.viewport = viewport;
		}

		@Override
		public int compare(Chunk chunk, Chunk farChunk) {
			Vector2 middle = viewport.findMiddle();
			Vector2 aChunk = chunk.getPosition().toVector2();
			Vector2 aFarChunk = farChunk.getPosition().toVector2();
			double dz0 = middle.sub(aChunk).mag();
			double dz1 = middle.sub(aFarChunk).mag();
			if (dz0 == dz1)
				return 0;
			if (dz0 > dz1)
				return -1;
			return 1;
		}
	}

	@Override
	public void thinkClient() {
		thinkClientReRenderChunks();
	}

	private void thinkClientReRenderChunks() {
		if (dirtyChunks.size() > 0) {
			Collections.sort(dirtyChunks, dirtyChunkSorter);
			// TODO: dirtyChunks is now sorted from close -> far,
			// so we can re-render the closest chunks to a vbo here
		}
	}

}
