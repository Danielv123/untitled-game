package net.allochie.st.shared.network.impl;

import java.io.IOException;

import net.allochie.st.shared.network.Encapsulator;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.network.packets.C00PlayerHandshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NetworkClientConnectionHandler extends ChannelInboundHandlerAdapter {
	private final NetworkManager manager;

	public NetworkClientConnectionHandler(NetworkManager manager) {
		this.manager = manager;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		manager.sendPacketToServer(new C00PlayerHandshake((byte) 0x01, "testplayer", "1234"));
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buffer = (ByteBuf) msg;
			Packet packet = Encapsulator.deencapsulatePacket(buffer);
			manager.clientQueue().queue(packet, manager.clientPlayer());
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
