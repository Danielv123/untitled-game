package net.allochie.st.shared.network;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

import net.allochie.st.server.ServerPlayer;
import net.allochie.st.shared.system.Side;

public class NetworkQueue {

	private static class QueuedPacket {
		public final Packet packet;
		public final WeakReference<ServerPlayer> player;

		public QueuedPacket(Packet packet, ServerPlayer player) {
			this.packet = packet;
			this.player = new WeakReference<ServerPlayer>(player);
		}
	}

	private final LinkedBlockingQueue<QueuedPacket> queue = new LinkedBlockingQueue<QueuedPacket>();
	private final ArrayList<QueuedPacket> drain = new ArrayList<QueuedPacket>();
	private final NetworkManager manager;
	private final String name;
	private final Side side;

	public NetworkQueue(NetworkManager manager, Side side, String name) {
		this.manager = manager;
		this.side = side;
		this.name = name;
	}

	public void queue(Packet packet, ServerPlayer player) {
		queue.offer(new QueuedPacket(packet, player));
	}

	public void think() {
		queue.drainTo(drain);
		Iterator<QueuedPacket> stack = drain.iterator();
		while (stack.hasNext()) {
			try {
				QueuedPacket obj = stack.next();
				ServerPlayer player = obj.player.get();
				if (player == null)
					throw new NetworkException("Packet enqueued without player or with dead reference.");
				Packet packet = obj.packet;
				if (side == Side.SERVER)
					player.handleServerPacket(packet);
				if (side == Side.CLIENT)
					player.handleClientPacket(packet);
			} catch (NetworkException exception) {
				exception.printStackTrace();
			}
		}
		drain.clear();
	}

}
