package net.allochie.st.client.render.world;

import java.util.WeakHashMap;

import org.lwjgl.opengl.GL11;

import net.allochie.st.client.ClientGame;
import net.allochie.st.client.ClientViewport;
import net.allochie.st.client.ClientWorld;
import net.allochie.st.shared.math.Vector2;
import net.allochie.st.shared.world.Block;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;

public class ClientWorldRenderer {

	private WeakHashMap<Chunk, Integer> bufferMap = new WeakHashMap<Chunk, Integer>();
	private int glxListId = -1;

	public ClientWorldRenderer() {
	}

	public void renderWorld(ClientGame theGame) {
		ClientWorld world = theGame.worldCache;
		if (world == null)
			return;
		Chunk dirty = world.selectDirtyChunk();
		if (dirty != null)
			paintChunk(world, dirty);

		Chunk gameChunk = world.getChunkFromBlockCoords((int) theGame.viewport.x, (int) theGame.viewport.y);
		if (gameChunk != null) {
			renderChunk(theGame.viewport, world, gameChunk);
			Chunk[] neighborChunks = world.getAdjacentChunks(gameChunk);
			for (int i = 0; i < neighborChunks.length; i++)
				if (neighborChunks[i] != null)
					renderChunk(theGame.viewport, world, neighborChunks[i]);
		}
	}

	private void paintChunk(World aworld, Chunk achunk) {
		if (!bufferMap.containsKey(achunk)) {
			int akey = nextBufferKey();
			if (akey >= 0)
				bufferMap.put(achunk, akey);
			else
				throw new RuntimeException("No render buffer free!");
		}
		int key = bufferMap.get(achunk);
		System.out.println("paintChunk: " + achunk + ": " + key);
		GL11.glNewList(glxListId + key, GL11.GL_COMPILE);
		for (int x = 0; x < achunk.width; x++) {
			for (int y = 0; y < achunk.height; y++) {
				Block bz0 = achunk.getBlock(new Vector2(x, y));
				if (bz0 != null)
					ClientBlockRenderer.renderBlockInWorld(aworld, achunk, bz0, x, y);
			}
		}
		GL11.glEndList();
	}

	private int nextBufferKey() {
		if (glxListId == -1)
			glxListId = GL11.glGenLists(2048);
		for (int i = 0; i < 2048; i++)
			if (!bufferMap.containsValue(i))
				return i;
		return -1;
	}

	private void renderChunk(ClientViewport viewport, ClientWorld aworld, Chunk achunk) {
		if (!bufferMap.containsKey(achunk)) {
			aworld.markChunkForRepaint(achunk);
			return;
		}
		int key = bufferMap.get(achunk);
		GL11.glPushMatrix();
		Vector2 coords = viewport.chunkCoordsToCamera(aworld, achunk);
		GL11.glTranslated(coords.x, coords.y, 0.0d);
		GL11.glCallList(glxListId + key);
		GL11.glPopMatrix();
	}

}
