package net.allochie.st.server;

import java.io.File;

import net.allochie.st.shared.system.TickThread;
import net.allochie.st.shared.world.World;
import net.allochie.st.shared.world.provider.ChunkProviderServer;

public class ServerGameSession {

	private World gameWorld;
	private ChunkProviderServer serverChunks;
	private TickThread thinkThread;

	public ServerGameSession(File savePath) {
		serverChunks = new ChunkProviderServer(savePath);
		gameWorld = new World(serverChunks);
	}

}
