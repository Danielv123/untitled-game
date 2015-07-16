package net.allochie.st.shared.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.allochie.st.server.ServerPlayer;
import net.allochie.st.shared.system.IThink;
import net.allochie.st.shared.system.Side;

public class NetworkManager implements IThink {

	private NetworkClient client;
	private NetworkServer server;

	protected NetworkQueue clientQueue;
	protected NetworkQueue serverQueue;

	protected ServerPlayer clientPlayer;
	protected HashMap<ServerPlayer, Channel> playerChannels;

	public NetworkManager() {
		// TODO Auto-generated constructor stub
	}

	public ServerPlayer clientPlayer() {
		return clientPlayer;
	}

	public NetworkQueue clientQueue() {
		return clientQueue;
	}

	public NetworkQueue serverQueue() {
		return serverQueue;
	}

	public void spinUpClient(String host, int port) {
		this.clientPlayer = new ServerPlayer();
		this.clientQueue = new NetworkQueue(this, Side.CLIENT, "Client network queue");
		this.client = new NetworkClient(this, host, port);
	}

	public void spinUpServer(int port) {
		this.playerChannels = new HashMap<ServerPlayer, Channel>();
		this.serverQueue = new NetworkQueue(this, Side.SERVER, "Server network queue");
		this.server = new NetworkServer(this, port);
	}

	public void shutdown() {
		if (this.client != null)
			this.client.shutdown();
		if (this.server != null)
			this.server.shutdown();
	}

	public ServerPlayer registerServerChannel(Channel channel) {
		synchronized (playerChannels) {
			ServerPlayer player = new ServerPlayer();
			playerChannels.put(player, channel);
			return player;
		}
	}

	public void deleteServerChannel(Channel channel) {
		synchronized (playerChannels) {
			Iterator<Entry<ServerPlayer, Channel>> itx = playerChannels.entrySet().iterator();
			while (itx.hasNext()) {
				Entry<ServerPlayer, Channel> binding = itx.next();
				if (binding.getValue().equals(channel))
					itx.remove();
			}
		}
	}

	public Channel playerChannel(ServerPlayer player) {
		synchronized (playerChannels) {
			return playerChannels.get(player);
		}
	}

	public void sendPacketToServer(Packet packet) {
		client.sendPacketToServer(packet);
	}

	public void sendPacketToPlayer(ServerPlayer player, Packet packet) {
		Channel channel = playerChannel(player);
		try {
			ByteBuf buf = channel.alloc().buffer();
			Encapsulator.encapsulatePacket(buf, packet);
			channel.writeAndFlush(buf);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public void sendPacketToAllPlayers(Packet packet) {
		synchronized (playerChannels) {
			Iterator<Channel> itx = playerChannels.values().iterator();
			while (itx.hasNext()) {
				try {
					Channel channel = itx.next();
					ByteBuf buf = channel.alloc().buffer();
					Encapsulator.encapsulatePacket(buf, packet);
					channel.writeAndFlush(buf);
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void thinkServer() {
		if (serverQueue != null)
			serverQueue.think();
	}

	@Override
	public void thinkClient() {
		if (clientQueue != null)
			clientQueue.think();
	}
}
