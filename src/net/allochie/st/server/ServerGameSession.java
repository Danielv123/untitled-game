package net.allochie.st.server;

import java.io.File;

import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.system.IThink;
import net.allochie.st.shared.system.ThinkerThread;
import net.allochie.st.shared.world.World;
import net.allochie.st.shared.world.provider.ChunkProviderServer;

public class ServerGameSession {

	private World gameWorld;
	private ChunkProviderServer serverChunks;
	private ThinkerThread thinkThread;
	private NetworkManager serverNetwork;

	public ServerGameSession(File savePath) {
		thinkThread = new ThinkerThread();
		serverChunks = new ChunkProviderServer(savePath);
		gameWorld = new World(serverChunks);
		thinkThread.startThread(true);
		thinkThread.addThinker(gameWorld);

		serverNetwork = new NetworkManager();
		thinkThread.addThinker(serverNetwork);
		serverNetwork.spinUpServer(9000);
	}

	public void shutdown() {
		thinkThread.abort();
		serverNetwork.shutdown();
	}
}
