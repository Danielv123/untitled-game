package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.world.ChunkCoord;

public class S03BlockChange extends Packet {

	public ChunkCoord chunk;
	public int x, y;
	public int block, blockData;

	public S03BlockChange() {
	}

	public S03BlockChange(ChunkCoord chunk, int x, int y, int block, int blockData) {
		this.chunk = chunk;
		this.x = x;
		this.y = y;
		this.block = block;
		this.blockData = blockData;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		this.chunk = new ChunkCoord(buffer.readInt(), buffer.readInt());
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.block = buffer.readInt();
		this.blockData = buffer.readInt();
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeInt(chunk.x);
		buffer.writeInt(chunk.y);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeInt(block);
		buffer.writeInt(blockData);
	}

}
