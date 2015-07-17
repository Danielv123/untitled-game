package net.allochie.st.shared.network;

public class Player {

	protected final NetworkManager manager;

	public Player(NetworkManager manager) {
		this.manager = manager;
	}

	public void handleClientPacket(Packet packet) throws NetworkException {
		throw new NetworkException("can't handle client packet here!");
	}

	public void handleServerPacket(Packet packet) throws NetworkException {
		throw new NetworkException("can't handle server packet here!");
	}

}
