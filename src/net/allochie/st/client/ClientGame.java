package net.allochie.st.client;

import net.allochie.st.shared.system.ThinkerThread;

public class ClientGame {

	private ClientViewport viewport;
	private ClientWorld worldCache;
	private ThinkerThread thinkThread;

	public ClientGame() {
		this.thinkThread = new ThinkerThread();
	}

	public ClientViewport getViewport() {
		return viewport;
	}

	public void shutdown() {
		thinkThread.abort();
	}
}
