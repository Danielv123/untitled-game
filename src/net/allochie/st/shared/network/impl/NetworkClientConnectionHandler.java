package net.allochie.st.shared.network.impl;

import java.io.IOException;

import net.allochie.st.shared.network.Encapsulator;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.network.packets.C00PlayerHandshake;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NetworkClientConnectionHandler extends LengthFieldBasedFrameDecoder {
	private final NetworkManager manager;

	public NetworkClientConnectionHandler(NetworkManager manager) {
		super(3 * 1024 * 1024, 0, 4, 0, 4);
		this.manager = manager;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		manager.sendPacketToServer(new C00PlayerHandshake((byte) 0x01, "testplayer", "1234"));
	}

	@Override
	protected Object decode(ChannelHandlerContext arg0, ByteBuf arg1) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(arg0, arg1);
		if (frame != null)
			try {
				Packet packet = Encapsulator.deencapsulatePacket(frame);
				manager.clientQueue().queue(packet, manager.clientPlayer());
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		return null;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
