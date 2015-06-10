package net.allochie.st.shared.world.provider;

import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.ChunkCoord;

public abstract class ChunkProvider {

	public abstract Chunk getChunkForCoords(ChunkCoord coord);

}
