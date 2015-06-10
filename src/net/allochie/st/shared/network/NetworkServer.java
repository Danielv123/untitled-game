package net.allochie.st.shared.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkServer {

	EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	ServerBootstrap b = new ServerBootstrap();

	public NetworkServer(int port) {
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.handler(new NetworkServerConnectionInitializer());
		try {
			b.bind(port).sync().channel().closeFuture().sync();
		} catch (InterruptedException interrupt) {
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
