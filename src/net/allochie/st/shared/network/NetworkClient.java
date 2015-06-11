package net.allochie.st.shared.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkClient {

	EventLoopGroup group = new NioEventLoopGroup();
	Bootstrap b = new Bootstrap();

	public NetworkClient(String host, int port) {
		b.group(group).channel(NioSocketChannel.class).handler(new NetworkClientConnectionInitializer());
		try {
			Channel ch = b.connect(host, port).sync().channel();

			ch.closeFuture().sync();
		} catch (InterruptedException interrupt) {
		} finally {
			group.shutdownGracefully();
		}

	}

}
