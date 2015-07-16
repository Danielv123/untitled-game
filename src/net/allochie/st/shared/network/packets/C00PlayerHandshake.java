package net.allochie.st.shared.network.packets;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.allochie.st.shared.network.Packet;

public class C00PlayerHandshake extends Packet {

	public byte proto;
	public String username;
	public String key;

	public C00PlayerHandshake() {
	}

	public C00PlayerHandshake(byte proto, String username, String key) {
		this.proto = proto;
		this.username = username;
		this.key = key;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		this.proto = buffer.readByte();
		this.username = (String) Packet.decodePrimitive(buffer);
		this.key = (String) Packet.decodePrimitive(buffer);
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeByte(proto);
		Packet.encodePrimitive(buffer, username);
		Packet.encodePrimitive(buffer, key);
	}

}
