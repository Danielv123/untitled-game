package net.allochie.st.shared.world.provider;

import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.ChunkCoord;
import net.allochie.st.shared.world.World;

public abstract class ChunkProvider {

	protected World world;

	public void setWorld(World world) {
		this.world = world;
	}

	public abstract Chunk getChunkForCoords(ChunkCoord coord);

}
