package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.world.ChunkCoord;

public class C01GetChunk extends Packet {

	public ChunkCoord coord;

	public C01GetChunk() {

	}

	public C01GetChunk(ChunkCoord coord) {
		this.coord = coord;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		coord = new ChunkCoord(buffer.readInt(), buffer.readInt());
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeInt(coord.x);
		buffer.writeInt(coord.y);
	}

}
