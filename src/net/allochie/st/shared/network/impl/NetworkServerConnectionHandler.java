package net.allochie.st.shared.network.impl;

import java.io.IOException;

import net.allochie.st.server.ServerPlayer;
import net.allochie.st.shared.network.Encapsulator;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NetworkServerConnectionHandler extends ChannelInboundHandlerAdapter {

	private final NetworkManager manager;
	private ServerPlayer hostedPlayer;

	public NetworkServerConnectionHandler(NetworkManager manager) {
		this.manager = manager;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		hostedPlayer = manager.registerServerChannel(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buffer = (ByteBuf) msg;
			Packet packet = Encapsulator.deencapsulatePacket(buffer);
			manager.serverQueue().queue(packet, hostedPlayer);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		manager.deleteServerChannel(ctx.channel());
	}

}
