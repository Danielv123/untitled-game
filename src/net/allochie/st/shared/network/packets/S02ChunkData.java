package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.world.Chunk;

public class S02ChunkData extends Packet {

	public int width, height;
	public int[][] blocks, blockData;

	public S02ChunkData() {
	}

	public S02ChunkData(Chunk achunk) {
		this.width = achunk.width;
		this.height = achunk.height;
		this.blocks = achunk.blocks;
		this.blockData = achunk.blockData;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		width = buffer.readInt();
		height = buffer.readInt();
		blocks = new int[width][height];
		blockData = new int[width][height];

		for (int u = 0; u < width; u++) {
			for (int v = 0; v < height; v++) {
				blocks[u][v] = buffer.readInt();
				blockData[u][v] = buffer.readInt();
			}
		}
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeInt(width);
		buffer.writeInt(height);
		for (int u = 0; u < width; u++) {
			for (int v = 0; v < height; v++) {
				buffer.writeInt(blocks[u][v]);
				buffer.writeInt(blockData[u][v]);
			}
		}
	}

}
