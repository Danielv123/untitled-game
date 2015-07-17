package net.allochie.st.shared.network.impl;

import net.allochie.st.shared.network.NetworkManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

public class NetworkClientConnectionInitializer extends ChannelInitializer<SocketChannel> {

	private final NetworkManager manager;

	public NetworkClientConnectionInitializer(NetworkManager manager) {
		this.manager = manager;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldPrepender(4));
		pipeline.addLast(new NetworkClientConnectionHandler(manager));
	}

}
