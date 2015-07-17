package net.allochie.st.shared.network;

import java.io.IOException;
import java.nio.channels.Channels;

import net.allochie.st.shared.network.impl.NetworkClientConnectionInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ChannelFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkClient {

	EventLoopGroup group = new NioEventLoopGroup();
	Bootstrap b = new Bootstrap();
	Channel ch;
	NetworkManager manager;

	public NetworkClient(NetworkManager manager, String host, int port) {
		this.manager = manager;
		b.group(group).channel(NioSocketChannel.class).handler(new NetworkClientConnectionInitializer(manager));
		try {
			ch = b.connect(host, port).sync().channel();
		} catch (InterruptedException interrupt) {
		} finally {
		}
	}

	public void shutdown() {
		try {
			ch.closeFuture().sync();
		} catch (InterruptedException interrupt) {
		} finally {
			group.shutdownGracefully();
		}
	}

	public void sendPacketToServer(Packet packet) {
		try {
			ByteBuf buf = ch.alloc().buffer(4096);
			Encapsulator.encapsulatePacket(buf, packet);
			ch.writeAndFlush(buf).sync();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (InterruptedException interrupt) {
		}
	}
}
