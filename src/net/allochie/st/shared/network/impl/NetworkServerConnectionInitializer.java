package net.allochie.st.shared.network.impl;

import net.allochie.st.shared.network.NetworkManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NetworkServerConnectionInitializer extends ChannelInitializer<SocketChannel> {

	private final NetworkManager manager;

	public NetworkServerConnectionInitializer(NetworkManager manager) {
		this.manager = manager;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new NetworkServerConnectionHandler(manager));
	}

}
