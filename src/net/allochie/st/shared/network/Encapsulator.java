package net.allochie.st.shared.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class Encapsulator {

	public static void encapsulatePacket(ByteBuf buffer, Packet packet) throws IOException {
		Class<? extends Packet> clazz = packet.getClass();
		Packet.encodePrimitive(buffer, clazz.getName());
		packet.writePacket(buffer);
	}

	public static Packet deencapsulatePacket(ByteBuf buffer) throws IOException {
		String clazzName = (String) Packet.decodePrimitive(buffer);
		try {
			Class<? extends Packet> clazz = (Class<? extends Packet>) Class.forName(clazzName);
			Packet packet = clazz.newInstance();
			packet.readPacket(buffer.slice());
			return packet;
		} catch (Exception ex) {
			if (ex instanceof IOException)
				throw (IOException) ex;
			throw new IOException("Decoding exception", ex);
		}
	}

}
