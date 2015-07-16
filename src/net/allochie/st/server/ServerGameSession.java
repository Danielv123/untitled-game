package net.allochie.st.server;

import java.io.File;

import net.allochie.st.shared.network.NetworkServer;
import net.allochie.st.shared.system.ThinkerThread;
import net.allochie.st.shared.world.World;
import net.allochie.st.shared.world.provider.ChunkProviderServer;

public class ServerGameSession {

	private World gameWorld;
	private ChunkProviderServer serverChunks;
	private ThinkerThread thinkThread;
	private NetworkServer server;

	public ServerGameSession(File savePath) {
		thinkThread = new ThinkerThread();
		serverChunks = new ChunkProviderServer(savePath);
		gameWorld = new World(serverChunks);
		thinkThread.startThread(true);
		thinkThread.addThinker(gameWorld);
		server = new NetworkServer(9000);
	}

	public void shutdown() {
		thinkThread.abort();
		server.shutdown();
	}
}
