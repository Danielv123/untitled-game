package net.allochie.st.server;

import java.io.File;

import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.system.ThinkerThread;
import net.allochie.st.shared.world.provider.ChunkProviderServer;

public class ServerGameSession {

	public ServerWorld gameWorld;
	public ChunkProviderServer serverChunks;
	public ThinkerThread thinkThread;
	public NetworkManager serverNetwork;

	public ServerGameSession(File savePath) {
		thinkThread = new ThinkerThread();

		thinkThread.startThread(true);
		
		serverChunks = new ChunkProviderServer(savePath);

		serverNetwork = new NetworkManager();
		thinkThread.addThinker(serverNetwork);
		
		gameWorld = new ServerWorld(serverChunks, serverNetwork);
		thinkThread.addThinker(gameWorld);

		serverNetwork.spinUpServer(this, 9000);

	}

	public void shutdown() {
		thinkThread.abort();
		serverNetwork.shutdown();
	}
}
