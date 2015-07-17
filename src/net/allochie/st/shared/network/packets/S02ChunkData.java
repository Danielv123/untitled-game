package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.ArrayList;

import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.ChunkCoord;

public class S02ChunkData extends Packet {

	public ChunkCoord coord;
	public int width, height;

	public int[][] blocks;
	public int[][] blockData;

	public S02ChunkData() {
	}

	public S02ChunkData(Chunk achunk) {
		this.coord = achunk.getPosition();
		this.width = achunk.width;
		this.height = achunk.height;
		this.blocks = achunk.blocks;
		this.blockData = achunk.blockData;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		coord = new ChunkCoord(buffer.readInt(), buffer.readInt());
		width = buffer.readInt();
		height = buffer.readInt();

		blocks = new int[width][height];
		blockData = new int[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				blocks[x][y] = buffer.readInt();
				blockData[x][y] = buffer.readInt();
			}
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeInt(coord.x);
		buffer.writeInt(coord.y);
		buffer.writeInt(width);
		buffer.writeInt(height);

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				buffer.writeInt(blocks[x][y]);
				buffer.writeInt(blockData[x][y]);
			}
	}

}
