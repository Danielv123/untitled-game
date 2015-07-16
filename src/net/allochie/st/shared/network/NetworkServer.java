package net.allochie.st.shared.network;

import net.allochie.st.shared.network.impl.NetworkServerConnectionInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetworkServer {

	private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private EventLoopGroup workerGroup = new NioEventLoopGroup();
	private ServerBootstrap b = new ServerBootstrap();
	private NetworkManager manager;

	public NetworkServer(NetworkManager manager, int port) {
		this.manager = manager;
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new NetworkServerConnectionInitializer(manager));
		try {
			Channel ch = b.bind(port).sync().channel();

			ch.closeFuture().sync();
		} catch (InterruptedException interrupt) {
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public void shutdown() {
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}
