package net.allochie.st.server;

import net.allochie.st.shared.init.Blocks;
import net.allochie.st.shared.network.NetworkException;
import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.Packet;
import net.allochie.st.shared.network.Player;
import net.allochie.st.shared.network.packets.C00PlayerHandshake;
import net.allochie.st.shared.network.packets.C01GetChunk;
import net.allochie.st.shared.network.packets.S00ServerHandshake;
import net.allochie.st.shared.network.packets.S01LevelData;
import net.allochie.st.shared.network.packets.S02ChunkData;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;

public class ServerPlayer extends Player {

	private final ServerGameSession game;

	private String username;
	protected World world;

	public ServerPlayer(ServerGameSession game, NetworkManager manager) {
		super(manager);
		this.game = game;
	}

	@Override
	public void handleServerPacket(Packet packet) throws NetworkException {
		System.out.println("handleServerPacket: " + packet.getClass().getName());
		if (packet instanceof C00PlayerHandshake) {
			C00PlayerHandshake handshake = (C00PlayerHandshake) packet;
			this.username = handshake.username;
			manager.sendPacketToPlayer(this, new S00ServerHandshake((byte) 0x01, "test server", "test motd", 0x01));
			putPlayerInWorld(game.gameWorld);
		} else if (packet instanceof C01GetChunk) {
			C01GetChunk achunk = (C01GetChunk) packet;
			Chunk chunk = world.getChunkFromCoords(achunk.coord);
			if (chunk != null) {
				sendChunkToPlayer(chunk);
				world.setBlockInWorld(0, 0, Blocks.foundation, 0);
			} else
				throw new NetworkException("Illegal chunk specified: " + achunk.coord);
		} else
			throw new NetworkException("Unhandled packet: " + packet.getClass().getName());
	}

	public void putPlayerInWorld(World theWorld) {
		this.world = theWorld;
		manager.sendPacketToPlayer(this, new S01LevelData(world.worldName, world.worldWidth, world.worldHeight));
	}

	public void sendChunkToPlayer(Chunk achunk) {
		manager.sendPacketToPlayer(this, new S02ChunkData(achunk));
	}

}
