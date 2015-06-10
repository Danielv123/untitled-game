package net.allochie.st.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;

public class ClientWorld extends World {

	private List<Chunk> dirtyChunks;
	private DirtyChunkSorter dirtyChunkSorter;

	public ClientWorld() {
		super();
		dirtyChunks = new ArrayList<Chunk>();
		dirtyChunkSorter = new DirtyChunkSorter();
	}

	@Override
	public void markBlockForUpdate(int x, int y) {
		// TODO: Find chunk, mark chunk dirty
	}

	private static class DirtyChunkSorter implements Comparator<Chunk> {
		@Override
		public int compare(Chunk chunk, Chunk farChunk) {
			// TODO Check the viewport here, figure out which chunk is
			// "closer" to the viewport. Return 0 if they're equidistant,
			// return -1 if farChunk closer, return 1 if chunk closer.
			return 0;
		}
	}

	@Override
	public void thinkClient() {
		thinkClientReRenderChunks();
	}

	private void thinkClientReRenderChunks() {
		if (dirtyChunks.size() > 0) {
			Collections.sort(dirtyChunks, dirtyChunkSorter);
		}
	}

}
