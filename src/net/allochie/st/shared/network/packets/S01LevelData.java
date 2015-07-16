package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.allochie.st.shared.network.Packet;

public class S01LevelData extends Packet {

	public String name;
	public int width;
	public int height;

	public S01LevelData() {
	}

	public S01LevelData(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		this.name = (String) Packet.decodePrimitive(buffer);
		this.width = buffer.readInt();
		this.height = buffer.readInt();
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		Packet.encodePrimitive(buffer, name);
		buffer.writeInt(width);
		buffer.writeInt(height);
	}

}
