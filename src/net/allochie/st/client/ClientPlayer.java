package net.allochie.st.client;

import net.allochie.st.shared.network.NetworkException;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.network.Player;
import net.allochie.st.shared.network.packets.C01GetChunk;
import net.allochie.st.shared.network.packets.S00ServerHandshake;
import net.allochie.st.shared.network.packets.S01LevelData;
import net.allochie.st.shared.network.packets.S02ChunkData;
import net.allochie.st.shared.network.packets.S03BlockChange;
import net.allochie.st.shared.world.ChunkCoord;
import net.allochie.st.shared.world.provider.ChunkProviderClient;

public class ClientPlayer extends Player {

	private final ClientGame game;

	private String serverName, serverMotd;

	public ClientPlayer(ClientGame game, NetworkManager manager) {
		super(manager);
		this.game = game;
	}

	@Override
	public void handleClientPacket(Packet packet) throws NetworkException {
		System.out.println("handleClientPacket: " + packet.getClass().getName());
		if (packet instanceof S00ServerHandshake) {
			S00ServerHandshake handshake = (S00ServerHandshake) packet;
			this.serverName = handshake.name;
			this.serverMotd = handshake.motd;
			game.currentPlayer = this;
		} else if (packet instanceof S01LevelData) {
			S01LevelData level = (S01LevelData) packet;
			game.chunkCache = new ChunkProviderClient(game, level.name, level.width, level.height);
			game.worldCache = new ClientWorld(game, game.chunkCache);
		} else if (packet instanceof S02ChunkData) {
			S02ChunkData chunk = (S02ChunkData) packet;
			game.chunkCache.fillInChunk(chunk.coord, chunk.width, chunk.height, chunk.blocks, chunk.blockData);
		} else if (packet instanceof S03BlockChange) {
			S03BlockChange block = (S03BlockChange) packet;
			game.worldCache.setBlockInWorld(block.x, block.y, block.block, block.blockData);
		} else
			throw new NetworkException("Unhandled packet: " + packet.getClass().getName());
	}

	public void requestChunk(ChunkCoord coord) {
		manager.sendPacketToServer(new C01GetChunk(coord));
	}

}
