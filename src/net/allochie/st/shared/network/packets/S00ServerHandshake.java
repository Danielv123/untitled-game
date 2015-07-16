package net.allochie.st.shared.network.packets;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.allochie.st.shared.network.Packet;

public class S00ServerHandshake extends Packet {

	public byte proto;
	public String name;
	public String motd;
	public int usertype;

	public S00ServerHandshake() {
	}

	public S00ServerHandshake(byte proto, String name, String motd, int usertype) {
		this.proto = proto;
		this.name = name;
		this.motd = motd;
		this.usertype = usertype;
	}

	@Override
	public void readPacket(ByteBuf buffer) throws IOException {
		this.proto = buffer.readByte();
		this.name = (String) Packet.decodePrimitive(buffer);
		this.motd = (String) Packet.decodePrimitive(buffer);
		this.usertype = buffer.readInt();
	}

	@Override
	public void writePacket(ByteBuf buffer) throws IOException {
		buffer.writeByte(proto);
		Packet.encodePrimitive(buffer, name);
		Packet.encodePrimitive(buffer, motd);
		buffer.writeInt(usertype);
	}

}
