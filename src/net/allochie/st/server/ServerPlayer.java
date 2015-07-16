package net.allochie.st.server;

import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.network.packets.C00PlayerHandshake;
import net.allochie.st.shared.network.packets.S00ServerHandshake;

public class ServerPlayer {

	private String username;
	private String serverName;
	private String serverMotd;

	public ServerPlayer() {
		// TODO Auto-generated constructor stub
	}

	public void handleServerPacket(NetworkManager manager, Packet packet) {
		System.out.println("handleServerPacket: " + packet.getClass().getName());
		if (packet instanceof C00PlayerHandshake) {
			C00PlayerHandshake handshake = (C00PlayerHandshake) packet;
			this.username = handshake.username;
			manager.sendPacketToPlayer(this, new S00ServerHandshake((byte) 0x01, "test server", "test motd", 0x01));
		}
	}

	public void handleClientPacket(NetworkManager manager, Packet packet) {
		// TODO Auto-generated method stub
		System.out.println("handleClientPacket: " + packet.getClass().getName());
		if (packet instanceof S00ServerHandshake) {
			S00ServerHandshake handshake = (S00ServerHandshake) packet;
			this.serverName = handshake.name;
			this.serverMotd = handshake.motd;
		}
	}

}
